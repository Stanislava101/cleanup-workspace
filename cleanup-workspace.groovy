import jenkins.model.*;

for (item in Jenkins.instance.items) {
  jobName = item.getFullDisplayName()
   if(!item.isBuilding()) {
     if (jobName.startsWith("clean")) {
       println("Wiping out workspaces of job " + jobName)
       customWorkspace = item.getCustomWorkspace()

       for (node in Jenkins.getInstance().getNodes())
       {
         nodeName = node.getDisplayName()
         workspacePath = node.getWorkspaceFor(item)
         if (customWorkspace != null)
         {
           workspacePath = node.getRootPath().child(customWorkspace)
         }
         if (workspacePath != null)
         {
           pathAsString = workspacePath.getRemote()
           if (workspacePath.exists())
           {
             String workspace = pathAsString
             File folder = new File(workspace)
             if(folder!=null &&folder.exists()){
               println "test"
             File[] files = new File(workspace).listFiles()
             files.each{
               println "in loop"
                if(it.isFile() == true){
         println it.name + "---S"
          }else if (it.isDirectory() == true){
            println it.name + "----D"
          }
          println folder
             }
             println("  [" + nodeName + "] Deleting " + pathAsString)
          //   workspacePath.deleteRecursive()
             println("  [" + nodeName + "] Deleted from location " + pathAsString)
             }
           }
         }
       }
     }
   }
}