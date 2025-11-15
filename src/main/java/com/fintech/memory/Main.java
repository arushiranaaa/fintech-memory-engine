package com.fintech.memory;

import com.fintech.memory.engine.*;
import com.fintech.memory.model.Transaction;
import com.fintech.memory.util.GCMonitor;

public class Main {

    public static void main(String[] args) throws Exception {

        int THREADS = 8;             
        int TOTAL_TX = 200_000;
        int POOL_SIZE = 10_000;

        MemoryManager mm = new MemoryManager(POOL_SIZE, 1_024_000);
        FraudDetector fd = new FraudDetector();
        TransactionProcessor tp = new TransactionProcessor(fd);

        MultiThreadedExecutor engine = new MultiThreadedExecutor(THREADS);

        Thread gcThread = GCMonitor.start();

        long start = System.currentTimeMillis();

        engine.processInParallel(TOTAL_TX, mm, tp);

        long end = System.currentTimeMillis();

        System.out.println("\n===== MULTITHREADED SUMMARY =====");
        System.out.println("Threads: " + THREADS);
        System.out.println("Total Transactions: " + tp.getProcessedCount());
        System.out.println("Frauds: " + tp.getFraudCount());
        System.out.println("Volume: " + tp.getTotalVolume());
        System.out.println("Total time: " + (end - start) + " ms");
        System.out.println("Throughput: " +
                (tp.getProcessedCount() * 1000.0 / (end - start)) +
                " TPS");

        gcThread.interrupt();
    }
}
