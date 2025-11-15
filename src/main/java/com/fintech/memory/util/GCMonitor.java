package com.fintech.memory.util;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCMonitor implements Runnable {
    private volatile boolean running = true;

    public static Thread start() {
        GCMonitor monitor = new GCMonitor();
        Thread t = new Thread(monitor, "GCMonitor");
        t.setDaemon(true);
        t.start();
        return t;
    }

    @Override
    public void run() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        while (running) {
            try { Thread.sleep(5000); }
            catch (InterruptedException e) { break; }

            System.out.println("----- GC Stats -----");
            for (GarbageCollectorMXBean gc : gcBeans) {
                System.out.println(gc.getName() + " | Count=" + gc.getCollectionCount() +
                        " | Time=" + gc.getCollectionTime());
            }
            System.out.println("--------------------");
        }
    }
}
