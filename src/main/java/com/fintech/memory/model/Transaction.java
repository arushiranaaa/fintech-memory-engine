package com.fintech.memory.model;

public class Transaction {
    private long id;
    private long userId;
    private double amount;
    private long timestamp;
    private String location;
    private String deviceId;
    private boolean fraudFlag;

    public Transaction() {}

    public void set(long id, long userId, double amount,
                    long timestamp, String location, String deviceId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.location = location;
        this.deviceId = deviceId;
        this.fraudFlag = false;
    }

    public void reset() {
        this.id = 0L;
        this.userId = 0L;
        this.amount = 0.0;
        this.timestamp = 0L;
        this.location = null;
        this.deviceId = null;
        this.fraudFlag = false;
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    public String getLocation() { return location; }
    public String getDeviceId() { return deviceId; }
    public boolean isFraudFlag() { return fraudFlag; }

    public void markFraud() { this.fraudFlag = true; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", location='" + location + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", fraudFlag=" + fraudFlag +
                '}';
    }
}
