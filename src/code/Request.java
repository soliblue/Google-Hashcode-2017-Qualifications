package code;

/**
 * Created by Soli on 28/03/2017.
 */
public class Request {
    int requestID;
    int videoID;
    int endPointID;
    int number;
    Request(int requestID,int videoID, int endPointID, int number){
        this.requestID = requestID;
        this.videoID = videoID;
        this.endPointID = endPointID;
        this.number = number;
    }
}
