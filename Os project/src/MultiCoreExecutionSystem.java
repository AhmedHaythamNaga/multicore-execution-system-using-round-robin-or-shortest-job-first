import java.io.*;
import java.util.*;

public class MultiCoreExecutionSystem {
    private static final SharedResources sharedResources = new SharedResources();

    public static void main(String[] args) throws IOException {

        parseProgram("/Users/ahmed/Documents/intelj/Os project/src/Program_2.txt");
        parseProgram("/Users/ahmed/Documents/intelj/Os project/src/Program_3.txt");

        // Choose scheduling algorithm: Round Robin (RR) or Shortest Job First (SJF)
        String schedulingAlgorithm = "RR";
        if (schedulingAlgorithm.equals("SJF")) {
            sortReadyQueueByInstructionCount();
        }

        MasterCore master = new MasterCore(schedulingAlgorithm, sharedResources);
        master.start();
    }

    private static void parseProgram(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> instructions = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            instructions.add(line.trim());
        }
        br.close();
        sharedResources.addToQueue(new Process(instructions));
    }

    private static void sortReadyQueueByInstructionCount() {
        List<Process> processes = new ArrayList<>();
        while (!sharedResources.isQueueEmpty()) {
            processes.add(sharedResources.pollFromQueue());
        }
        processes.sort(Comparator.comparingInt(Process::getInstructionCount));
        for (Process process : processes) {
            sharedResources.addToQueue(process);
        }
    }
}
