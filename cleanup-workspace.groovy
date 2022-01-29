import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

long startTime = System.currentTimeMillis();

//Get value from String Parameter
MAX_BUILDS = 3

for (job in Jenkins.instance.items)
{
    SortedMap<Integer,List<String>> sprint_paths_map  = new TreeMap<Integer, List<String>>(new DescOrder());
        if(job.name =="cleanup-workspace"){
            continue;
        }
    println "\n ***** Job Name: "+job.name+" *****"
    
        if(job.workspace!=null && job.workspace!="")  //Check if there is a workspace associated with the Job
        {
            println "Workspace path : " + job.workspace
            
            String workspace = job.workspace
            
            File folder = new File(workspace)
            
            if(folder!=null && folder.exists()) //Check if the Workspace folder exists
            {
                // Get all files and folders within the Workspace of current job. 
                // Iterate through only folders and sort em by Modified Date.
                
              	sprint_number = null
              	
              	//Determine which sprint_number to delete
                File[] files = new File(workspace).listFiles().each{
                  
                  if(it!=null && it.exists() && !it.isFile()){
				  
                    sprint_number = it.name.find( /\d+/ )!=null ? it.name.find( /\d+/ ).toInteger() : null
  
                    if(sprint_number!=null)
                    {
                      if(sprint_paths_map.containsKey(sprint_number))
                          sprint_paths_map.get(sprint_number).add(it.absolutePath)
                      else
                      {
                          List<String> list = new ArrayList<String>()
                          list.add(it.absolutePath);
                          sprint_paths_map.put(sprint_number, list)
                      }
                    }
                    sprint_number = null
                  }
                }
              	
              	int count = 0;
              
                //Delete the folders based on the build number
				
              	for(Map.Entry<Integer, List<String>> entry : sprint_paths_map.entrySet())
              	{
              		if(count >= MAX_BUILDS) {
                  	//	deleteFolders(entry.getValue())
                      println "Deleted"
                    }
                  	
                    else {
                      	println "Save - " + entry
                    }
                    
                  count++
                }
              	
            }
            else
            {
                println "Workspace is empty or doesn't exist"
            }
        }
        else
        {
            println "No Workspace is associated with this job"
        }
    
}

/*
void deleteFolders(List<String> paths){
  
  println "To Delete"
  
  for(String path : paths)
  {
    File file = new File(path)
    
    if( ! file.isFile() && file.exists())
    {
        file.deleteDir()
        .println "Deleted -" + path
    }
  }
}
*/
//For Descending order TreeMap

class DescOrder implements Comparator<Integer> {
	@Override
	public int compare(Integer o1, Integer o2) {
	    return o2.compareTo(o1);
	}
}

long endTime   = System.currentTimeMillis();
long totalTime = (endTime - startTime)/1000;

println "Total Run time in seconds : " + totalTime