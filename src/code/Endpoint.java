package code;

/**
 * Created by Soli on 27/03/2017.
 */
public class Endpoint {
    int endPointID;
    int latencyToMainDataCenter;
    ConnectedCacheServer [] connectedCacheServers;
    Endpoint(int endPointID, int latencyToMainDataCenter, int numberOfConnectedServers){
        this.endPointID = endPointID;
        this.latencyToMainDataCenter = latencyToMainDataCenter;
        this.connectedCacheServers = new ConnectedCacheServer[numberOfConnectedServers];
    }
}
