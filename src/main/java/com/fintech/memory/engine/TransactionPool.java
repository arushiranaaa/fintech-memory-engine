package com.fintech.memory.engine;

import com.fintech.memory.model.Transaction;
import java.util.ArrayDeque;
import java.util.Queue;

public class TransactionPool {
    private final Queue<Transaction> pool;
    private final int maxSize;

    public TransactionPool(int maxSize) {
        this.pool = new ArrayDeque<>(maxSize);
        this.maxSize = maxSize;
    }

    public Transaction acquire() {
        Transaction tx = pool.poll();
        return (tx == null) ? new Transaction() : tx;
    }

    public void release(Transaction tx) {
        if (pool.size() < maxSize) {
            tx.reset();
            pool.offer(tx);
        }
    }

    public int getCurrentPoolSize() { return pool.size(); }
}
