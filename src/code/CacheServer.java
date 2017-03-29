package code;

/**
 * Created by Soli on 28/03/2017.
 */
public class CacheServer {
    int cacheServerID;
    int remainingSpace;
    int [] latenciesToEndPoints;
    int [] containsVideo;
    CacheServer(int cacheServerID,int remainingSpace, int numberOfEndPoints,int numberOfVideos){
        this.cacheServerID = cacheServerID;
        this.remainingSpace = remainingSpace;
        this.latenciesToEndPoints = new int[numberOfEndPoints];
        this.containsVideo = new int[numberOfVideos];
    }
}
