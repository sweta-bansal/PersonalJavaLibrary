


public ClusterExistsValidation(String clusterName) {
        emrCheck = new ExecuteShellCommand("[ $(aws emr list-clusters --active | grep " + clusterName + " | wc -l) -gt 0 ]"));
    }
    
public CheckStatusOfClusterById(String clusterId, String clusterName, String status, String timestamp) {

        checkCluster = new ExecuteShellCommand("[ $(aws emr list-clusters --cluster-states " + status
                + " --created-after " + timestamp + " | grep " + clusterId + " | wc -l) -gt 0 ]"));
    }
    
public KillCluster(String clusterName)  {
        
        killCluster = new ExecuteShellCommand("clusterId=$(" +
                "aws emr list-clusters --active" +
                " | grep " + clusterName + " -B 1" +
                " | grep \\\"j" +
                " | sed 's/\\\"Id\\\": \\\"//g' |" +
                " sed 's/\\\",//g');" +
                " aws emr terminate-clusters --cluster-ids $clusterId")));

        
    }
    
    
