import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

//manager.listener.logger.println new Date(System.currentTimeMillis()).format('MM/dd/yyyy hh:mm:ss a') + " / " + " -- Start Time" 

//Get value from String Parameter
MAX_BUILDS = 3

 //   def list =[]
  //      int count1 =0

for (job in Jenkins.instance.items) 
{
  	int count = 0
  	boolean check = false

    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
            continue;
        }
        if(job.workspace == null){
            println "null"
        }


        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
        String workspace = job.workspace
       // int workspaceLength = workspace.length()
        //int removeSymbol = workspaceLength -2

     //       if(!(workspace.charAt(removeSymbol) == '@')){
         //       long workspaceLength2 = job.workspace.length()
         //       long fileSizeInKB = workspaceLength2/1024
          //      println fileSizeInKB 
            println "Workspace path : " + job.workspace
       //     println workspace.charAt(removeSymbol)
            File workspaceFile = new File(workspace)

            File folder = new File(workspace) 
            println folder
             String folderString = folder.getParentFile().getName()
             int folderLength = folderString.length()
            int removeSymbol = folderLength -2
            println removeSymbol
          
            if(folder!=null && folder.exists() && folderString.charAt(removeSymbol) == '@' ) 
            {
                //   long fileSizeInKB = file.length()/1024
                //  println fileSizeInKB    
              //   println "${folder} ${folder.size()} ${new Date(folder.lastModified())}"
                
                 File[] files = new File(workspace).listFiles()
                
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
         //   }
        }
        else
        {
            println "No Workspace associated with this job"
        }
    }

    // def list2 = []
    // def dir = new File("/storage/jenkins/workspace/validate-product-awsgc")
    // dir.eachFileRecurse(FileType.FILES) {
    //     file ->
    //     list2 <<file
    // }
    // list.each{
    //     println it.path
    // }


// def fileList = "ls -la /storage/jenkins/workspace/".execute().text
// def files =[]
// fileList.eachLine {
// files.add(it)
// }
// for(it in files){
//     println it
// }


// File dir = new File("/storage/jenkins/workspace")
// dir.eachFile{f ->
// println "${f} ${f.size()} ${new Date(f.lastModified())}"
// }

