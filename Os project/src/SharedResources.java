import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SharedResources {
    private final Queue<Process> readyQueue = new LinkedList<>();
    private final ConcurrentHashMap<String, Integer> sharedMemory = new ConcurrentHashMap<>();

    public synchronized void addToQueue(Process process) {
        readyQueue.add(process);
    }

    public synchronized Process pollFromQueue() {
        return readyQueue.poll();
    }

    public synchronized boolean isQueueEmpty() {
        return readyQueue.isEmpty();
    }

    public ConcurrentHashMap<String, Integer> getSharedMemory() {
        return sharedMemory;
    }

    public synchronized int getQueueSize() {
        return readyQueue.size();
    }
}
