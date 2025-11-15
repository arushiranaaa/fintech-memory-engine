package com.fintech.memory.engine;

import com.fintech.memory.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadedExecutor {

    private final ExecutorService executor;

    public MultiThreadedExecutor(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void processInParallel(
            int totalTransactions,
            MemoryManager mm,
            TransactionProcessor tp) throws InterruptedException {

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < totalTransactions; i++) {

            int finalI = i;

            Future<?> f = executor.submit(() -> {
                try {
                    Transaction tx = mm.acquireTransaction();

                    tx.set(
                        finalI + 1,
                        ThreadLocalRandom.current().nextInt(1, 10001),
                        100 + ThreadLocalRandom.current().nextDouble() * 100_000,
                        System.currentTimeMillis(),
                        "IN-" + ThreadLocalRandom.current().nextInt(1, 29),
                        "device-" + ThreadLocalRandom.current().nextInt(1, 5001)
                    );

                    tp.process(tx, mm);
                    mm.releaseTransaction(tx);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            futures.add(f);
        }

        // Wait safely for tasks
        for (Future<?> f : futures) {
            try {
                f.get(30, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                System.out.println("Task timeout!");
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
