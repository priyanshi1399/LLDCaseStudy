package RateLimiter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

public class LeakyBucket {

    private final long capacity; //Maximum number of request allowed in the bucket
    private final double leakRate; //how fast the leak
    private final Queue<Instant> bucket; //holds timestamp of requests
    private Instant lastLeakTimeStamp;

    public LeakyBucket(long capacity,double leakRate){
        this.capacity=capacity;
        this.leakRate=leakRate;
        bucket=new LinkedList<>();
        this.lastLeakTimeStamp=Instant.now();
    }

    public synchronized boolean allowRequest(){
        leak(); //first leak out any request based on elapsed time
        if(bucket.size()<capacity){
            bucket.offer(Instant.now()); //Add the new request to the bucket
            System.out.println("Request Allowed bucket size is "+bucket.size());
            return true;
        }
        System.out.println("request Denied bucket is full ");
        return false;



    }

    public void  leak(){
        Instant now=Instant.now();
        long elapsedMilis=now.toEpochMilli()-lastLeakTimeStamp.toEpochMilli();
        int leakedItems=(int) (elapsedMilis*leakRate/1000.0);

                //how many items should have leaked
        for(int i=0;i<leakedItems && !bucket.isEmpty();i++){
            bucket.poll();
        }


        if(leakedItems>0 && !bucket.isEmpty()){
            System.out.println("Leaked  "+leakedItems + " bucket size "+bucket.size());
        }
        lastLeakTimeStamp=now;
    }

    public static void main(String[] args) throws InterruptedException {

        LeakyBucket bucket=new LeakyBucket(5,1);
        for(int i=1;i<=10;i++){
            System.out.println("Request are "+i);
            bucket.allowRequest();
            Thread.sleep(500);
        }
    }

}
