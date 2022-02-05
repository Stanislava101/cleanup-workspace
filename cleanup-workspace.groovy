import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

MAX_BUILDS =4


for (job in Jenkins.instance.items) 
{

  	int count = 0

    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
            continue;
        }

        if(job.workspace!=null && job.workspace!="") 
        {

        String workspace = job.workspace

            println "Workspace path : " + job.workspace

            File folder = new File(workspace) 
          
            if(folder!=null && folder.exists()) 
            {
                File[] files = new File(workspace).listFiles()

                def newList = files.sort()
                 newList.each{
                   check =true
                        if(!it.isFile())      
                     {      String sub = it.path
                            File[] subDir = new File(sub).listFiles()
                            for(s in subDir){
                                println "Sub dir is " + s
                            }



                         if(count > MAX_BUILDS){
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " -- Save" 
                         }
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

