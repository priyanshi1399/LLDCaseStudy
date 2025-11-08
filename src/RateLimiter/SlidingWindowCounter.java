package RateLimiter;

import java.time.Instant;

public class SlidingWindowCounter {

    private final long windowSizeInSeconds;
    private final long maxRequestsPerWindow;
    private long currentWindowStart;
    private long previousWindowCount;
    private long currentWindowCount;

    public SlidingWindowCounter(long windowSizeInSeconds,long maxRequestsPerWindow){
        this.windowSizeInSeconds=windowSizeInSeconds;
        this.maxRequestsPerWindow=maxRequestsPerWindow;
        this.currentWindowCount=0;
        this.previousWindowCount=0;
        this.currentWindowStart= Instant.now().getEpochSecond();
    }


    public synchronized boolean allowRequest(){

        long now=Instant.now().getEpochSecond();
        long timePassedInWindow=now-currentWindowStart;

        if(timePassedInWindow>=windowSizeInSeconds){
            previousWindowCount=currentWindowCount;
            currentWindowCount=0;
            currentWindowStart=now;
            timePassedInWindow=0;
        }

        //calculate weighted count of the requests

        double weightedCount=((previousWindowCount*windowSizeInSeconds-timePassedInWindow)/(double) windowSizeInSeconds)+currentWindowCount;

        if(weightedCount<maxRequestsPerWindow){
            currentWindowCount++;
            return true;
        }

        return false;

    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindowCounter limiter = new SlidingWindowCounter(10, 5);

        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.allowRequest();
            System.out.println("Request at " + i  + " at " + Instant.now().getEpochSecond() + (allowed ? "->allowed" : "denied"));
            Thread.sleep(2000);
        }
    }
}
