package com.fintech.memory.engine;

import com.fintech.memory.model.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class FraudScorer {

    private final Map<Long, String> lastLocation = new HashMap<>();
    private final Map<Long, Integer> txnFrequency = new HashMap<>();
    private final Map<Long, String> lastDevice = new HashMap<>();

    public double score(Transaction tx) {

        // 1. Amount score (bigger amount → bigger risk)
        double amountScore = Math.min(tx.getAmount() / 100000.0, 1.0);

        // 2. Device score (new device → risk)
        String prevDevice = lastDevice.get(tx.getUserId());
        double deviceScore = (prevDevice != null && !prevDevice.equals(tx.getDeviceId())) ? 1.0 : 0.0;

        // Update device
        lastDevice.put(tx.getUserId(), tx.getDeviceId());

        // 3. Location score (location change → risk)
        String prevLoc = lastLocation.get(tx.getUserId());
        double locationScore = (prevLoc != null && !prevLoc.equals(tx.getLocation())) ? 1.0 : 0.0;

        // Update location
        lastLocation.put(tx.getUserId(), tx.getLocation());

        // 4. Frequency score (too many txns → risk)
        int count = txnFrequency.getOrDefault(tx.getUserId(), 0) + 1;
        txnFrequency.put(tx.getUserId(), count);
        double frequencyScore = Math.min(count / 10.0, 1.0); // after 10 txns → risk

        // Weighted fraud score
        return
                0.4 * amountScore +
                0.3 * deviceScore +
                0.2 * locationScore +
                0.1 * frequencyScore;
    }
}
