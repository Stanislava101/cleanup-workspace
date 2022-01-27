import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

//Get value from String Parameter
MAX_BUILDS = 3
@NonCPS
def func(){
for (job in Jenkins.instance.items) 
{
  
  	int count = 0
  	
    println "\n ***Job Name: "+job.name+"***"
    
        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
            println "Workspace path : " + job.workspace
            
            String workspace = job.workspace
            
            File folder = new File(workspace)
            
            if(folder!=null && folder.exists()) //Check if the Workspace folder exists
            {
                // Get all files and folders within the Workspace of current job. 
                //Iterate through only folders and sort em by Modified Date.
                
                File[] files = new File(workspace).listFiles().sort(){
                a,b -> b.lastModified().compareTo a.lastModified()
                }
                .each{
                    if(!it.isFile()) //Check only for folders
                    {
                        if(count < MAX_BUILDS)
                            println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " -- Save" 
                        else
                        {
                            println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " ** Deleted" 
                            
                        }
                        count++
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
}