import java.util.*;
import java.util.stream.Collectors;

public class MasterCore extends Thread {
    private final String schedulingAlgorithm;
    private final SharedResources sharedResources;

    public MasterCore(String schedulingAlgorithm, SharedResources sharedResources) {
        this.schedulingAlgorithm = schedulingAlgorithm;
        this.sharedResources = sharedResources;
    }

    @Override
    public void run() {
        System.out.println("Master Core: Starting execution...");
        List<SlaveCore> slaveCores = Arrays.asList(
                new SlaveCore(sharedResources),
                new SlaveCore(sharedResources)
        );
        slaveCores.forEach(Thread::start);

        while (!sharedResources.isQueueEmpty() || slaveCores.stream().anyMatch(Thread::isAlive)) {
            System.out.println("Ready Queue: " + sharedResources.getQueueSize() + " processes waiting.");
        }
        System.out.println("Master Core: Execution complete.");
    }
}
