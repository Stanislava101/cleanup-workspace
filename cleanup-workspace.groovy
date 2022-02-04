import hudson.model.*
// For each job
for (item in Hudson.instance.items)
{
  jobName = item.getFullDisplayName()
    if (jobName == "clean_jenkins_machines")
    {
      println("Wiping out workspaces of job " + jobName)
      
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
        if (jobName == "seed-takts")
    {
      println("Wiping out workspaces of job " + jobName)
      
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
        if (jobName == "build-product-buildpacks")
    {
      println("Wiping out workspaces of job " + jobName)
      
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
        if (jobName == "validate-dashboards")
    {
      println("Wiping out workspaces of job " + jobName)
      
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
}