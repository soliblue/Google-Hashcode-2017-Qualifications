package code;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Soli on 27/03/2017.
 */
public class Interpreter {
    double score;
    int numberOfVideos;
    int numberOfEndpoints;
    int numberOfRequests;
    int numberOfCacheServers;
    int cacheServerCapacity;
    Video [] videos;
    CacheServer [] cacheServers;
    Endpoint [] endpoints;
    Request [] requests;

    Interpreter(String filePath){
        // Reading the file
        String content = readFile(filePath, StandardCharsets.UTF_8);
        String [] lines = content.split("\n");
        // Setting the amount of everything
        score = 0;
        String [] line = lines[0].split(" ");
        numberOfVideos = Integer.parseInt(line[0]);
        numberOfEndpoints = Integer.parseInt(line[1]);
        numberOfRequests = Integer.parseInt(line[2]);
        numberOfCacheServers = Integer.parseInt(line[3]);
        cacheServerCapacity = Integer.parseInt(line[4]);
        // Setting the videos
        line = lines[1].split(" ");
        videos = new Video[numberOfVideos];
        for(int videoID = 0;videoID <numberOfVideos;videoID++){
            Video video = new Video(videoID,Integer.parseInt(line[videoID]));
            videos[videoID] = video;
        }
        // Setting the endpoints && CacheServers
        cacheServers = new CacheServer[numberOfCacheServers];
        for(int cacheServerID = 0;cacheServerID<numberOfCacheServers;cacheServerID++){
            CacheServer cacheServer = new CacheServer(cacheServerID,cacheServerCapacity,numberOfEndpoints,numberOfVideos);
            cacheServers[cacheServerID] = cacheServer;
        }
        endpoints = new Endpoint[numberOfEndpoints];
        int currentLine = 1;
        for(int endPointID = 0; endPointID <numberOfEndpoints;endPointID++){
            currentLine++;
            line = lines[currentLine].split(" ");
            int numberOfConnectedCacheServers = Integer.parseInt(line[1]);
            int latencyToMainDataCenter = Integer.parseInt(line[0]);
            Endpoint endpoint = new Endpoint(endPointID,latencyToMainDataCenter,numberOfConnectedCacheServers);
            for(int connectedCacheServerNumber = 0;connectedCacheServerNumber<numberOfConnectedCacheServers;
                connectedCacheServerNumber++){
                currentLine++;
                line = lines[currentLine].split(" ");
                int cacheServerID = Integer.parseInt(line[0]);
                int latencyFromCacheServerToEndPoint = Integer.parseInt(line[1]);
                cacheServers[cacheServerID].latenciesToEndPoints[endPointID] = latencyFromCacheServerToEndPoint;
                endpoint.connectedCacheServers[connectedCacheServerNumber] = new ConnectedCacheServer();
                endpoint.connectedCacheServers[connectedCacheServerNumber].cacheServerID = cacheServerID;
                endpoint.connectedCacheServers[connectedCacheServerNumber].latency = latencyFromCacheServerToEndPoint;
            }
            // Sorting the Cache Servers after their best latency
            QuickSort.quickSortConnectedCacheServers(endpoint.connectedCacheServers,0,numberOfConnectedCacheServers -1);
            endpoints[endPointID] = endpoint;
        }
        // Setting the requests
        requests = new Request[numberOfRequests];
        for(int requestID = 0; requestID <numberOfRequests;requestID++){
            currentLine++;
            line = lines[currentLine].split(" ");
            int videoID = Integer.parseInt(line[0]);
            int endPointID = Integer.parseInt(line[1]);
            Request request = new Request(requestID,videoID,endPointID,
                    Integer.parseInt(line[2]));
            for(ConnectedCacheServer connectedCacheServer: endpoints[endPointID].connectedCacheServers){
                videos[videoID].relevantCacheServers.add(connectedCacheServer.cacheServerID);
            }
            videos[videoID].relevantRequests.add(requestID);
            requests[requestID] = request;
        }
    }

    void solve(){
        for(Video video:videos){
            calculateValue(video);
            video.relevantCacheServers.remove(video.cacheServerID);
        }
        QuickSort.quickSortVideos(videos,0,numberOfVideos -1);
        while(videos[videos.length-1].value != 0){
            Video video = videos[videos.length-1];
            CacheServer cacheServer = cacheServers[video.cacheServerID];
            if(cacheServer.remainingSpace >= video.size){
                cacheServers[video.cacheServerID].remainingSpace -=
                        video.size;
                cacheServers[video.cacheServerID].containsVideo[video.videoID] = 1;
            }
            video.value = 0;
            calculateValue(video);
            QuickSort.quickSortVideos(videos,0,numberOfVideos -1);
        }
        score = getScore();
    }

    void calculateValue(Video video){
        for(int relevantCacheServer : video.relevantCacheServers){
           if(cacheServers[relevantCacheServer].remainingSpace >= video.size &&
                   cacheServers[relevantCacheServer].containsVideo[video.videoID] == 0){
               double value = getImprovement(video, relevantCacheServer);
               if(value > video.value){
                   video.value = value;
                   video.cacheServerID = relevantCacheServer;
               }
           }
        }
    }

    double getImprovement(Video video, int cacheServerID){
        double improvement = 0;
        for(int relevantRequest : video.relevantRequests){
            int bestAvailableCacheServerID = -1;
            for(ConnectedCacheServer connectedCacheServer:endpoints[requests[relevantRequest].endPointID].connectedCacheServers){
                int relevantCacheServer = connectedCacheServer.cacheServerID;
                if(cacheServers[relevantCacheServer].containsVideo[video.videoID] == 1){
                    bestAvailableCacheServerID = relevantCacheServer;
                    break;
                }
            }
            if(bestAvailableCacheServerID == -1){
                Request request = requests[relevantRequest];
                Endpoint endpoint = endpoints[request.endPointID];
                CacheServer cacheServer = cacheServers[cacheServerID];
                improvement+= (request.number *
                        (endpoint.latencyToMainDataCenter - cacheServer.latenciesToEndPoints[endpoint.endPointID]));
            } else{
                Request request = requests[relevantRequest];
                int endPointID = endpoints[request.endPointID].endPointID;
                CacheServer bestAvailableCacheServer = cacheServers[bestAvailableCacheServerID];
                improvement+= (request.number *
                        (bestAvailableCacheServer.latenciesToEndPoints[endPointID] -
                                cacheServers[cacheServerID].latenciesToEndPoints[endPointID])) ;
            }
        }
        return improvement / video.size;
    }

    double getScore(){
        double totalTimeSaved = 0;
        double requestsCount = 0;
        for(Request request : requests) {
            int bestAvailableLatency = getBestAvailableLatency(request);
            totalTimeSaved += (endpoints[request.endPointID].latencyToMainDataCenter - bestAvailableLatency) *
                    request.number;
            requestsCount += request.number;
        }
        return (totalTimeSaved / requestsCount) * 1000;
    }

    int getBestAvailableLatency(Request request){
        int bestAvailableLatency = endpoints[request.endPointID].latencyToMainDataCenter;
        for(CacheServer cacheServer : cacheServers){
            if(bestAvailableLatency > cacheServer.latenciesToEndPoints[request.endPointID] &&
                    cacheServer.containsVideo[request.videoID] == 1){
                bestAvailableLatency = cacheServer.latenciesToEndPoints[request.endPointID];
            }
        }
        return bestAvailableLatency;
    }

    String getSolution(){
        String solution = numberOfCacheServers + "\n";
        for (CacheServer cacheServer : cacheServers){
            solution += cacheServer.cacheServerID;
            for(int videoID = 0;videoID<numberOfVideos;videoID++){
                if (cacheServer.containsVideo[videoID] == 1){
                    solution += " " + videoID;
                }
            }
            solution += "\n";
        }
        return solution;
    }

    @NotNull
    private static String readFile(String path, Charset encoding){
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        } catch (IOException e) {
            System.out.println("File didn't load correctly! Please check path!");
            return "";
        }
    }
}
