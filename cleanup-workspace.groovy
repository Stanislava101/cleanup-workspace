import hudson.model.*
// For each job
for (item in Hudson.instance.items)
{
  jobName = item.getFullDisplayName()
  // check that job is not building
  //if (!item.isBuilding())
  //{
    // TODO: Modify the following condition to select which jobs to affect
    if (jobName == "validate-product-gcp")
    {
      println("Wiping out workspaces of job " + jobName)
      customWorkspace = item.getCustomWorkspace()
      println("Custom workspace = " + customWorkspace)
      
      for (node in Hudson.getInstance().getNodes())
      {
        println("  Node: " + node.getDisplayName())
        workspacePath = node.getWorkspaceFor(item)
        if (workspacePath == null)
        {
          println("    Could not get workspace path")
        }
        else
        {
          if (customWorkspace != null)
          {
            workspacePath = node.getRootPath().child(customWorkspace)
          }

          pathAsString = workspacePath.getRemote()
          if (workspacePath.exists())
          {
            println workspacePath
            String w = workspacePath
            File[] files = new File("./").listFiles()
            files.each{
              println it.name
            }
            for(f in files){
              println f
            }
           // workspacePath.deleteRecursive()
            println("    Deleted from location " + pathAsString)
          }
          else
          {
            println("    Nothing to delete at " + pathAsString)
          }
        }
      }
    }
  //}
 
}