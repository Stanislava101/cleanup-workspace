import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

MAX_BUILDS = 3

for (job in Jenkins.instance.items) 
{
  	int count = 0
  	boolean check = false

    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
            continue;
        }

        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
        String workspace = job.workspace

         //       long workspaceLength2 = job.workspace.length()
         //       long fileSizeInKB = workspaceLength2/1024
          //      println fileSizeInKB 
            println "Workspace path : " + job.workspace

            File folder = new File(workspace) 
            println folder
             String folderString = folder.getName()
             int folderLength = folderString.length()
            int removeSymbol = folderLength -2
                            println folderString.charAt(removeSymbol)
                            println folder.getName()

                 File[] files
                
                if(folder.getName().charAt(removeSymbol) == '@'){
                    println "remove @"
                    int length = folder.getName().length()
                    int nameLength = length - 2
                     workspace = "/storage/jenkins/workspace/"+folder.getName().substring(0,nameLength)
                     println "The workspace is "
                     println workspace
                     if(workspace == "/storage/jenkins/workspace/validate-product-aws3"){
                         println "aws3 found"
                         String dirs = workspace+"/39075"
                         String dirs2 = workspace+"/39076"
                         String dirs3 = workspace+"/39078"
                         files = new File(dirs).listFiles()
                         files = new File(dirs2).listFiles()
                         files = new File(dirs3).listFiles()

                     }
                     files = new File(workspace).listFiles()
                     println "${workspace} ${workspace.size()}"

                     println "with @"
                long workspaceLength2 = job.workspace.length()
                long fileSizeInKB = workspaceLength2/1024
                println fileSizeInKB 
                         

                //                       files.sort{
                //  a,b -> b.lastModified() <=> a.lastModified()
                //  }
                files.each{
                    println it
                }


                 files.each{
                   check =true
                        if(!it.isFile())         //isDirectory, it.isFile()
                     {      
                         if(count < MAX_BUILDS){
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
          
            if(folder!=null && folder.exists()) 
            {
                 files = new File(workspace).listFiles()
                                println ("without @")
                 files.sort{
                 a,b -> b.lastModified() <=> a.lastModified()
                 }

                println ("process 2 without @")
                 files.each{
                   check =true
                        if(!it.isFile())         //isDirectory, it.isFile()
                     {      
                         if(count < MAX_BUILDS){
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " -- Save" 
                         }
                         else
                         {
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " ** Deleted" 
                            
                         }
                         count++
                     }
                     
                 
                 }
             
          /*  if(check == true){
                         println "Item found"
                     }
            */
            if(check == false){
                println "Workspace is empty or doesn't exist"
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

