package com.com.ycj.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


/**
 * Created by Mtime on 2017/7/19.
 */
public class SchedualTest {


    private static int TASK_COUNT = 100*100*100;


    public static void main(String[] args) {
        final HashedWheelTimer timer = new HashedWheelTimer();

        IntStream.range(0,TASK_COUNT).forEach(index ->{
            //HashedWheelTimer timer = new HashedWheelTimer();
            timer.newTimeout(new TimerTask() {
                public void run(Timeout timeout) throws Exception {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    localDateTime = localDateTime.plusNanos(1000);
                    System.out.println("timeout "+ localDateTime.toString());
                    System.out.println("执行的index："+index);
                    //Thread.sleep(100000);

                }
            }, 1, TimeUnit.NANOSECONDS);

        });
    }
}
