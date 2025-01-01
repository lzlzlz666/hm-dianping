package com.hmdp;

import com.hmdp.utils.RedisWorker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HmDianPingApplicationTests {

    @Resource
    private RedisWorker redisWorker;

    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void testIdWorker() throws InterruptedException {
        // 创建一个计数器，确保所有任务完成
        CountDownLatch latch = new CountDownLatch(300);

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisWorker.nextId("order");
                System.out.println("id = " + id); // 或者 log.info("id = " + id);
            }
            latch.countDown(); // 每个任务完成后减少计数
        };

        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }

        latch.await(); // 等待所有任务完成
    }


    @Test
    void print() {
        System.out.println("666");
    }
}
