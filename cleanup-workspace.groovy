import hudson.model.*
// For each job
for (item in Hudson.instance.items)
{
  jobName = item.getFullDisplayName()
  // check that job is not building
  //if (!item.isBuilding())
  //{
    // TODO: Modify the following condition to select which jobs to affect
    if (jobName == "build-product-buildpacks")
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
            File[] files = new File(pathAsString).listFiles()
            files.each{
              println it
            }
          //  workspacePath.deleteRecursive()
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
  //else
 // {
   // println("Skipping job " + jobName + ", currently building")
  //}
}