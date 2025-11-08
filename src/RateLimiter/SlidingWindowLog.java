package RateLimiter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLog {

    private final long windowSizeInSeconds;
    private final long maxRequestsPerWindow;
    private final Queue<Long> requestLog;

    public SlidingWindowLog(long windowSizeInSeconds, long maxRequestsPerWindow) {
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.requestLog = new LinkedList<>();
    }

    public synchronized boolean allowRequest() {

        long now = Instant.now().getEpochSecond();

        long windowStart = now - windowSizeInSeconds;
        //Remove timestamp that are outside of current window

        while (!requestLog.isEmpty() && requestLog.peek() <= windowStart) {
            requestLog.poll();
        }

        if (requestLog.size() < maxRequestsPerWindow) {
            requestLog.offer(now);
            return true;
        }

        return false; //we have exceeded the limit for the window deny the request

    }


    public static void main(String[] args) throws InterruptedException {
        SlidingWindowLog limiter = new SlidingWindowLog(10, 3);

        for (int i = 1; i <= 5; i++) {
            boolean allowed = limiter.allowRequest();
            System.out.println("Request at " + i  + " at " + Instant.now().getEpochSecond() + (allowed ? "->allowed" : "denied"));
            Thread.sleep(2000);
        }
    }
}
