package com.fintech.memory.engine;

import com.fintech.memory.model.Transaction;

public class TransactionProcessor {
    private final FraudDetector fraudDetector;
    private long processedCount = 0L;
    private long fraudCount = 0L;
    private double totalVolume = 0.0;
    private final FraudScorer fraudScorer = new FraudScorer();

    public TransactionProcessor(FraudDetector fraudDetector) {
        this.fraudDetector = fraudDetector;
    }

    public void process(Transaction tx, MemoryManager mm) {
    // Compute fraud score
    double score = fraudScorer.score(tx);
    // If score >= 0.8 → high risk
    if (score >= 0.8) {
        tx.markFraud();
        fraudCount++;
    }

    processedCount++;
    totalVolume += tx.getAmount();

    mm.writeToDirectBuffer(tx.getAmount());
}


    public long getProcessedCount() { return processedCount; }
    public long getFraudCount() { return fraudCount; }
    public double getTotalVolume() { return totalVolume; }
}
