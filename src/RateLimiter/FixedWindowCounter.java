package RateLimiter;

import java.time.Instant;

public class FixedWindowCounter {

    private final long windowSizeInSeconds; //size of each window in second
    private final long maxRequestsPerWindow; //no of maximum request allowed per window
    private long currentWindowStart;
    private long requestCount;

    public FixedWindowCounter(long windowSizeInSeconds,long maxRequestsPerWindow){
        this.windowSizeInSeconds=windowSizeInSeconds;
        this.maxRequestsPerWindow=maxRequestsPerWindow;
        this.currentWindowStart= Instant.now().getEpochSecond();
        this.requestCount=0;
    }

    public synchronized boolean allowRequest(){
        long now=Instant.now().getEpochSecond();
        //check if we have moved to new window
        if(now-currentWindowStart>maxRequestsPerWindow){
            currentWindowStart=now; //start a new window
            requestCount=0; //reset the counter for a new window
        }

        if(requestCount<maxRequestsPerWindow){
            requestCount++; //increment count for this window
            return true; //allow the request
        }
        return false; // we have exceeded the limit increment the window deny the request
    }

    public static void main(String[] args) throws InterruptedException {

        FixedWindowCounter window=new FixedWindowCounter(5,3);
        for(int i=1;i<=10;i++){
            boolean allowed=window.allowRequest();
            System.out.println("Requests is "+i+  (allowed? " allowed": " denied"));
            Thread.sleep(1000);
        }
    }
}
