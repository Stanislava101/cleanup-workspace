// Check if a slave has < 10 GB of free space, wipe out workspaces if it does

import hudson.model.*;
import hudson.util.*;
import jenkins.model.*;
import hudson.FilePath.FileCallable;
import hudson.slaves.OfflineCause;
import hudson.node_monitors.*;


def performCleanup(def node, def items) {
  
  for (item in items) {
    jobName = item.getFullDisplayName()
    
    println("Cleaning " + jobName)
    
    if(item instanceof com.cloudbees.hudson.plugins.folder.AbstractFolder) {
      	performCleanup(node, item.items)
    	continue
    }
    
    if (item.isBuilding()) {
      println(".. job " + jobName + " is currently running, skipped")
      continue
    }
    
    println(".. wiping out workspaces of job " + jobName)

    workspacePath = node.getWorkspaceFor(item)
    if (workspacePath == null) {
      println(".... could not get workspace path")
      continue
    }
    
    println(".... workspace = " + workspacePath)
    
    pathAsString = workspacePath.getRemote()
    if (workspacePath.exists()) {
      String workspace = workspacePath
      File folder = new File(workspace)
      if(folder!=null && folder.exists()){
        File files = new File []
        files.each{
         println it.name
        }
       println folder
      }
    //  workspacePath.deleteRecursive()
      println(".... deleted from location " + pathAsString)
    } else {
      println(".... nothing to delete at " + pathAsString)
    }
  }  
}


for (node in Jenkins.instance.nodes) {
    computer = node.toComputer()
    if (computer.getChannel() == null) continue

    rootPath = node.getRootPath()
    size = DiskSpaceMonitor.DESCRIPTOR.get(computer).size
    roundedSize = size / (1024 * 1024 * 1024) as int

    println("node: " + node.getDisplayName() + ", free space: " + roundedSize + "GB")
    computer.setTemporarilyOffline(true, new hudson.slaves.OfflineCause.ByCLI("disk cleanup"))
  
    performCleanup(node, Jenkins.instance.items)
  
    computer.setTemporarilyOffline(false, null)

}