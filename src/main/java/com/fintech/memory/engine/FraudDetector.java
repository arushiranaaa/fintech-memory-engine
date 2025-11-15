package com.fintech.memory.engine;

import com.fintech.memory.model.Transaction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FraudDetector {
    private final Map<Long, Set<String>> userDevices = new HashMap<>();

    public boolean isFraud(Transaction tx) {
        Set<String> devices = userDevices.computeIfAbsent(
            tx.getUserId(), k -> new HashSet<>());

        boolean newDevice = !devices.contains(tx.getDeviceId());
        boolean highAmount = tx.getAmount() > 50000.0;

        devices.add(tx.getDeviceId());

        if (highAmount && newDevice) {
            tx.markFraud();
            return true;
        }
        return false;
    }
}
