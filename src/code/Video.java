package code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Soli on 27/03/2017.
 */
public class Video {
    int videoID;
    int size;
    double value;
    int cacheServerID;
    HashSet <Integer> relevantCacheServers;
    List <Integer> relevantRequests;
    Video(int videoID,int size){
        this.videoID = videoID;
        this.size = size;
        this.value = 0;
        this.cacheServerID = -1;
        this.relevantCacheServers = new <Integer> HashSet();
        this.relevantRequests = new <Integer> ArrayList<Integer>();
    }
}
