public class SlaveCore extends Thread {
    private final SharedResources sharedResources;

    public SlaveCore(SharedResources sharedResources) {
        this.sharedResources = sharedResources;
    }

    @Override
    public void run() {
        while (!sharedResources.isQueueEmpty()) {
            Process process = sharedResources.pollFromQueue();
            if (process != null) {
                while (process.executeNext(sharedResources)) {
                    try {
                        Thread.sleep(500); // Simulate execution time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Process " + process.getProcessID() + " completed."); // Access via getter
            }
        }
    }
}

