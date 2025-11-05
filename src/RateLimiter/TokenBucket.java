package RateLimiter;

import java.time.Instant;

public class TokenBucket {

    private final double fillRate; //Rate at which token are added in the bucket
    private final long capacity;
    private double tokens; //how many token
    private Instant lastRefillTimeStamp; //last time we refilled the bucket


    public TokenBucket(long capacity , double fillRate){
        this.capacity=capacity;
        this.fillRate=fillRate;
        this.tokens=capacity; //start with a full bucket
        this.lastRefillTimeStamp=Instant.now();

    }


    public synchronized  boolean allowRequest(int tokens){
        refill();
        if(this.tokens<tokens){
            System.out.println("Denied Request , Available tokens ="+ this.tokens);
            return false;
        }
        this.tokens-=tokens; //consume the tokens
        System.out.println("Allowed Request , left tokens ="+ this.tokens);
        return true; //allow the request

    }

    public void refill(){
        Instant now=Instant.now();
        //calculate how many tokens to add based on time elapsed
        double tokensToAdd=(now.toEpochMilli()-lastRefillTimeStamp.toEpochMilli()) *fillRate/1000.0;
        this.tokens=Math.min(capacity,this.tokens+tokensToAdd);
        //Add the tokens but don't exceed the capacity
        this.lastRefillTimeStamp=now;

    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket tc=new TokenBucket(5,1);
        tc.allowRequest(2);
        tc.allowRequest(3);
        tc.allowRequest(1);

        System.out.println("waiting for 3 seconds to refill ");
        Thread.sleep(3000);
        tc.allowRequest(2);
    }




}
