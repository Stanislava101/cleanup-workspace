import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

MAX_BUILDS =4


for (job in Jenkins.instance.items) 
{

  	int count = 0
  	boolean check = false

    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
            continue;
        }
        if(job.name =="validate-build-bom"){
          continue;
        }
                if(job.name =="validate-canary-aws-pr"){
          continue;
        }
                if(job.name =="validate-concourse-pipeline"){
          continue;
        }
        if(job.name == "validate-dashboards"){
                         println "dashboards found"
                         String w = job.workspace
                      //   File files = new File(w).listFiles()
                        File f = new File("/storage/jenkins/workspace/validate-dashboards/products")
                        if(f == null){
                          println "folder is null"
                        }else if (f.exists() == false){
                          println f
                          println "Folder doesn't exists"
                        } else{
                          println "weird"
                          File theF = new File("/storage/jenkins/workspace/validate-dashboards/products").listFiles()
                          theF.each{
                            println it.name + " ---S"
                          }
                        }
                     }

        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {

        String workspace = job.workspace

            println "Workspace path : " + job.workspace

            File folder = new File(workspace) 
            println folder
              File[] files
             String folderString = folder.getName()
             int folderLength = folderString.length()
            int removeSymbol = folderLength -2
                            println folderString.charAt(removeSymbol)
                            println folder.getName()
            if(workspace == "/storage/jenkins/workspace/validate-dashboards"){
                         println "dashboards found"
                       //  files = new File("/storage/jenkins/workspace/validate-product-aws3/39326").listFiles()
                      //  files = new File("/storage/jenkins/workspace/validate-product-aws3/39344").listFiles()
                        File f = new File("/storage/jenkins/workspace/validate-product-aws3/39340")
                        if(f.isFile() == true){
                          println "True"
                        } else if (f.isDirectory() == true){
                          println "False"
                        }
                         files.each{
                           println it.name
                         }
                           files.each{
                           println it
                         }

                     }

                
                if(folder.getName().charAt(removeSymbol) == '@'){
                    println "remove @"
                    int length = folder.getName().length()
                    int nameLength = length - 2
                     workspace = "/storage/jenkins/workspace/"+folder.getName().substring(0,nameLength)
                     println "The workspace is "
                     println workspace
                     if(workspace == "/storage/jenkins/workspace/validate-product-aws3"){
                         println "aws3 found"
                       //  files = new File("/storage/jenkins/workspace/validate-product-aws3/39326").listFiles()
                      //  files = new File("/storage/jenkins/workspace/validate-product-aws3/39344").listFiles()
                        File f = new File("/storage/jenkins/workspace/validate-product-aws3/39340")
                        if(f.isFile() == true){
                          println "True"
                        } else if (f.isDirectory() == true){
                          println "False"
                        }
                         files.each{
                           println it.name
                         }
                           files.each{
                           println it
                         }

                     }
                   //  files = new File(workspace).listFiles()
                     println "${workspace} ${workspace.size()}"
                     for(file in files){
                         println "file is " + file
                     }

                     println "with @"
                long workspaceLength2 = job.workspace.length()
                long fileSizeInKB = workspaceLength2/1024
                println fileSizeInKB 
                         

                //                       files.sort{
                //  a,b -> b.lastModified() <=> a.lastModified()
                //  }
                files.each{
                    println "Path is " + it
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
            //     files.sort{
                 //a,b -> b.lastModified() <=> a.lastModified()
             //    a,b -> b.lastModified().compareTo(a.lastModified())
             //    }
                def newList = files.sort()
                 newList.each{
                   check =true
                        if(!it.isFile())         //isDirectory, it.isFile()
                     {      String sub = it.path
                            File[] subDir = new File(sub).listFiles()
                            for(s in subDir){
                                println "Sub dir is " + s
                            }



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

