📌 Overview

This project simulates a real-time FinTech transaction engine, optimized for high throughput, low latency, and intelligent fraud detection.

It combines JVM memory management techniques, object pooling, off-heap memory, multithreading, and a weighted fraud scoring model to simulate how actual payment systems (UPI, Visa, Stripe, PayPal, Razorpay) process and screen transactions.

✨ Key Features
🔥 1. High-Performance Transaction Processing
-Processes 200,000+ transactions per run
-Achieves ~680,000 TPS on consumer hardware
-Built with ExecutorService multithreading

🧠 2. Fraud Scoring Model (Weighted)
Generates a fraud probability (0–1) using engineered risk features:
-Amount risk
-Device change
-Location change
-Frequency of transactions
-If score ≥ 0.8 → transaction marked fraudulent.

⚡ 3. JVM Memory Management
Includes:
-Custom Transaction Object Pool
-Reduced GC pressure
-Direct (off-heap) ByteBuffer usage
-Efficient heap utilization
-GC monitoring using JVM MXBeans

🏎 4. Optimized Performance Architecture
-Parallel computation
-Zero GC spikes
-Low-latency risk assessment
-Memory-efficient data reuse

📊 5. Live GC & Memory Usage Monitoring
Get real-time:
-Eden/Young GC count & pauses
-Heap usage
-Pool size
-Throughput metrics

🧩 System Architecture
┌────────────────────────┐
│        Main.java        │
│  - Coordinates engine   │
│  - Starts worker pool   │
└───────────┬────────────┘
            │
┌───────────▼───────────┐
│ MultiThreadedExecutor │
│  - 8 threads           │
│  - Parallel processing │
└───────────┬───────────┘
            │
┌───────────▼────────────┐
│  TransactionProcessor   │
│  - Fraud scoring        │
│  - Memory writes        │
└───────────┬────────────┘
            │
┌───────────▼─────────────┐
│     MemoryManager        │
│ - Off-heap buffer        │
│ - Transaction pool       │
└──────────────────────────┘

📂 Project Structure
src/main/java/com/fintech/memory/
│── Main.java
│── model/
│   └── Transaction.java
│── engine/
│   ├── FraudDetector.java
│   ├── FraudScorer.java
│   ├── MemoryManager.java
│   ├── TransactionPool.java
│   ├── TransactionProcessor.java
│   └── MultiThreadedExecutor.java
└── util/
    └── GCMonitor.java

⚙️ How to Run
1. Compile
cd src/main/java
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { javac $_.FullName }

2. Run
cd ../../..
java -cp src/main/java com.fintech.memory.Main

📈 Sample Output
===== MULTITHREADED SUMMARY =====
Threads: 8
Total Transactions: 195836
Frauds: 80401
Volume: 9.81B
Total time: 289 ms
Throughput: 677,633 TPS

🛡 Fraud Scoring Logic
score = 
    0.4 * amountScore +
    0.3 * deviceChange +
    0.2 * locationChange +
    0.1 * frequencyScore;

Thresholds:
≥ 0.8 → HIGH RISK
0.5–0.8 → MEDIUM RISK
< 0.5 → LOW RISK

🤝 Contributing
PRs, issues and suggestions are welcome.

⭐ If you like this project, give it a star!
It helps others find it and supports my work.
