package com.fintech.memory.engine;

import com.fintech.memory.model.Transaction;
import java.nio.ByteBuffer;

public class MemoryManager {
    private final TransactionPool transactionPool;
    private final ByteBuffer directBuffer;

    public MemoryManager(int poolSize, int directBufferBytes) {
        this.transactionPool = new TransactionPool(poolSize);
        this.directBuffer = ByteBuffer.allocateDirect(directBufferBytes);
    }

    public Transaction acquireTransaction() {
        return transactionPool.acquire();
    }

    public void releaseTransaction(Transaction tx) {
        transactionPool.release(tx);
    }

    public void writeToDirectBuffer(double value) {
        if (directBuffer.remaining() < Double.BYTES) {
            directBuffer.clear();
        }
        directBuffer.putDouble(value);
    }

    public void logMemoryUsage() {
        Runtime rt = Runtime.getRuntime();
        long total = rt.totalMemory() / (1024 * 1024);
        long free = rt.freeMemory() / (1024 * 1024);
        long used = total - free;

        System.out.println("[Memory] Used: " + used + " MB, Free: " + free + " MB, Total: " + total +
                " MB, Pool size: " + transactionPool.getCurrentPoolSize());
    }
}
