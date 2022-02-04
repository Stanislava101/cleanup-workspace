import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import java.util.*


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
                          Calendar calendar = Calendar.getInstance();
                          calendar.add(Calendar.DAY_OF_MONTH, -7)
                          Date lastDate = calendar.getTime()
                        long diff = getDateDiff(lastDate, new Date(f.lastModified()), TimeUnit.DAYS)
                        if(Math.abs(diff) <=7)
                        {
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


    def getDateDiff(Date date1, Date date2, TimeUnit timeUnit){
      long diffInMillies = date2.getTime() - date1.getTime()
      return timeUnit.convert(diffInMiilies, TimeUnit.MILLISECONDS)
    }