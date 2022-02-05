import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*


//Get value from String Parameter
MAX_BUILDS = 2

for (job in Jenkins.instance.items) 
{
          if(job.name =="cleanup-workspace"){
            continue;
        }
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
                
                File[] files = new File(workspace).listFiles()
                files.sort(){
                a,b -> b.lastModified() <=> a.lastModified()
                }
                files.each{
                    if(!it.isFile()) //Check only for folders
                    {
                      println it.path
                      String subWorkspace = it.path
                      File[] subFiles = new File(subWorkspace).listFiles()
                      subFiles.sort(){
                          a,b -> b.lastModified() <=> a.lastModified()
                      }
                        for(f in subFiles){

                      //    println f
                      if(!it.isFile()){
                      String subSubWorkspace = it.path
                      File[] subSubFiles = new File(subSubWorkspace).listFiles()
                        subSubFiles.sort(){
                          a,b -> b.lastModified() <=> a.lastModified()
                      }
                      for(sf in subSubFiles){
                          println "Sub sub file " + sf.name
                      }
                   //     if(count < MAX_BUILDS){
                          def millis1 = System.currentTimeMillis()
                          def millis2 = f.lastModified()
                          long diff = millis1 - millis2
                          long diffDays = diff / (24* 60*60*1000)
                          println diffDays
                          if(diffDays<365){
                            println new Date(f.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + f.name + " -- Save" 
                        }
                    //    }
                        else
                        {
                            println new Date(f.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + f.name + " ** Deleted" 
                        //    it.deleteDir()
                        }
                      }
                        }
                        if(diffDays<365)
                            println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " -- Save" 
                        else
                        {
                            println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " ** Deleted" 
                        //    it.deleteDir()
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