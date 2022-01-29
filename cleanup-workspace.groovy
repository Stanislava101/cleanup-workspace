import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import groovy.io.FileType

//manager.listener.logger.println new Date(System.currentTimeMillis()).format('MM/dd/yyyy hh:mm:ss a') + " / " + " -- Start Time" 

//Get value from String Parameter
MAX_BUILDS = 1

    def list =[]
        int count1 =0

for (job in Jenkins.instance.items) 
{
  	int count = 0
  	boolean check = false

    println "\n ***Job Name: "+job.name+"***"
        if(job.name =="cleanup-workspace"){
         //   println "testtt"
            continue;
        }
        if(job.workspace == null){
            println "null"
        }


        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
        String workspace = job.workspace
                list.add(workspace)
        int workspaceLength = workspace.length()
        int removeSymbol = workspaceLength -2

            if(!(workspace.charAt(removeSymbol) == '@')){
                long workspaceLength2 = job.workspace.length()
                long fileSizeInKB = workspaceLength2/1024
                println fileSizeInKB 
            println "Workspace path : " + job.workspace
            println workspace.charAt(removeSymbol)
             
            File folder = new File(workspace)

          
            if(folder!=null && folder.exists()) 
            {
                
                 File[] files = new File(workspace).listFiles()
                
                 files.sort{
                 a,b -> b.lastModified() <=> a.lastModified()
                 }
    def fileList = "ls -la validate-product-awsgc".execute().text
    def files =[]
    fileList.eachLine {
    files.add(it)
}
println "//////"
for(it in files){
    println it
}

                 files.each{
                   check =true
                        if(!it.isFile())         //isDirectory
                     {      println "in loop"
                         if(count < MAX_BUILDS){
                             println "test1"
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " -- Save" 
                         }
                         else
                         {
                             println "test2"
                             println new Date(it.lastModified()).format('MM/dd/yyyy hh:mm:ss a') + " /" + it.name + " ** Deleted" 
                            
                         }
                         count++
                     }
                 
                 }
             
            if(check == true){
                         println "Item found"
                     }
            else if(check == false){
                println "Item not found"
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
