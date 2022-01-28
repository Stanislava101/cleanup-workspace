import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

//manager.listener.logger.println new Date(System.currentTimeMillis()).format('MM/dd/yyyy hh:mm:ss a') + " / " + " -- Start Time" 

//Get value from String Parameter
MAX_BUILDS = 1


for (job in Jenkins.instance.items) 
{
  
  	int count = 0
  	
    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
            println "testtt"
            continue;
        }
        if(job.workspace == null){
            println "nulll"
        }


    
        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
        String workspace = job.workspace
        int workspaceLength = workspace.length()
        int removeSymbol = workspaceLength -2
            if(!(workspace.charAt(removeSymbol) == '@')){
            println "Workspace path : " + job.workspace
            println workspace.charAt(removeSymbol)

           // String workspace = job.workspace
             
            File folder = new File(workspace)
            
            if(folder!=null && folder.exists()) 
            {
                println "test"

                 File[] files = new File(workspace).listFiles()
                 files.sort{
                 a,b -> b.lastModified() <=> a.lastModified()
                 }
                println "check test"
                 files.each{
   
                     if(it.isDirectory() == true) //isDirectory
                     {      println "in loop"
                         if(count < MAX_BUILDS){
                             println "test1"
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " -- Save" 
                         }
                         else
                         {
                             println "test2"
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " ** Deleted" 
                            
                         }
                         count++
                     }
                 
                 }
             }
            }
            else
            {
                println "Workspace is empty or doesn't exist"
            }
        }
        else
        {
            println "No Workspace associated with this job"
        }
    }



//manager.listener.logger.println new Date(System.currentTimeMillis()).format('MM/dd/yyyy hh:mm:ss a') + " / " + " -- End Time" 