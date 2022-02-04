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
                        if(count < MAX_BUILDS){
                          GregorianCalendar cal1 = new GregorianCalendar(f.lastModified())
                          GregorianCalendar cal2 = new GregorianCalendar(2022,2,4)
                          long ms1 = cal1.getTime().getTime()
                          long ms2 - cal2.getTime().getTime()
                          long difMs = ms2-ms1
                          long msPerDay = 1000*60*60*24
                          double days = difMs/msPerDay
                          
                            println new Date(f.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + f.name + " -- Save" 
                        }
                        else
                        {
                            println new Date(f.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + f.name + " ** Deleted" 
                        //    it.deleteDir()
                        }
                      }
                        }
                        if(count < MAX_BUILDS)
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