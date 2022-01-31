import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

//Get value from String Parameter
MAX_BUILDS = 3

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
        if(job.name =="validate-iac-descriptors"){
            MAX_BUILDS = 5
        }
        if(job.name == "validate-product-ac2"){
            MAX_BUILDS = 2
        }
        if(job.workspace == null){
            println "null"
        }


        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
        String workspace = job.workspace
       // int workspaceLength = workspace.length()
        //int removeSymbol = workspaceLength -2

         //       long workspaceLength2 = job.workspace.length()
         //       long fileSizeInKB = workspaceLength2/1024
          //      println fileSizeInKB 
            println "Workspace path : " + job.workspace
       //     println workspace.charAt(removeSymbol)
            File workspaceFile = new File(workspace)

            File folder = new File(workspace) 
            println folder
             String folderString = folder.getName()
             int folderLength = folderString.length()
            int removeSymbol = folderLength -2
                            println "char"
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
                     files = new File(workspace)
                }
          
            if(folder!=null && folder.exists()) 
            {
              //   println "${folder} ${folder.size()} ${new Date(folder.lastModified())}"
                println folderString.charAt(removeSymbol)
                 files = new File(workspace).listFiles()
                
                 files.sort{
                 a,b -> b.lastModified() <=> a.lastModified()
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

