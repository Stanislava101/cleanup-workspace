import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

MAX_BUILDS = 3

//Jenkins jenkins = Jenkins.instance
//def jenkinsNodes = jenkins.nodes
for (job in Jenkins.instance.items) 
{

  	int count = 0
  	boolean check = false
    //  Jenkins jenkins2 = Jenkins.instance
    //  def jenkinsNodes = jenkins2.nodes
     // for(Node node in jenkinsNodes){
     //     println"'$node.nodeName'"
     // }

    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
            continue;
        }
        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
          customWorkspace = job.getCustomWorkspace()
          println "Custom " + customWorkspace

       String workspace = job.workspace

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
                         files = new File("/39075").listFiles()

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



