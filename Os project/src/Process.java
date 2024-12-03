import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Process {
    private static int counter = 0;
    private final int processID;
    private final List<String> instructions;
    private int programCounter = 0;

    public Process(List<String> instructions) {
        this.processID = counter++;
        this.instructions = instructions;
    }

    public int getInstructionCount() {
        return instructions.size();
    }

    public boolean executeNext(SharedResources sharedResources) {
        if (programCounter < instructions.size()) {
            String instruction = instructions.get(programCounter);
            System.out.println("Executing (Process " + processID + "): " + instruction);
            executeInstruction(instruction, sharedResources.getSharedMemory());
            programCounter++;
            return true;
        }
        return false;
    }

    private void executeInstruction(String instruction, ConcurrentHashMap<String, Integer> sharedMemory) {
        String[] parts = instruction.split(" ");
        switch (parts[0]) {
            case "assign":
                String var = parts[1];
                if (parts[2].equals("input")) {
                    sharedMemory.put(var, new Random().nextInt(100));
                } else if (parts[2].equals("add")) {
                    sharedMemory.put(var, sharedMemory.get(parts[3]) + sharedMemory.get(parts[4]));
                } else if (parts[2].equals("subtract")) {
                    sharedMemory.put(var, sharedMemory.get(parts[3]) - sharedMemory.get(parts[4]));
                }
                break;
            case "print":
                System.out.println("Process " + processID + " Output: " + sharedMemory.get(parts[1]));
                break;
        }
    }

    public int getProcessID() { // Getter for processID
        return processID;
    }
}
