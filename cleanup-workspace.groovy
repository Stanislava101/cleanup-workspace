import jenkins.model.*;

for (item in Jenkins.instance.items) {
  jobName = item.getFullDisplayName()
   if(!item.isBuilding()) {
     if (jobName.startsWith(JOB_PREFIX)) {
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
             println("  [" + nodeName + "] Deleting " + pathAsString)
          //   workspacePath.deleteRecursive()
             println("  [" + nodeName + "] Deleted from location " + pathAsString)
           }
         }
       }
     }
   }
}