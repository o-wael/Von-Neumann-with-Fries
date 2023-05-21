package memory;

import parser.Parser;

public class MainMemory {

    private static final MainMemory mainMemoryInstance = new MainMemory();
    private static final int memorySize = 2048;
    private static final int instructionsStart = 0;
    private static final int instructionsEnd = 1023;
    private static final int dataStart = 1024;
    private static final int dataEnd = 2047;
    private static int curNumOfInstructions = 0;
    private int[] mainMemory;
    private String[] assemblyMemory;

    private MainMemory() {
        mainMemory = new int[memorySize];
        assemblyMemory = new String[instructionsEnd + 1];
    }

    public static MainMemory getMainMemoryInstance() {
        return mainMemoryInstance;
    }

    public int memRead(int address) {
        return mainMemory[address];
    }

    public void memWrite(int address, int value) {
        mainMemory[address] = value;
    }

    public String assemblyRead(int address) {
        return assemblyMemory[address];
    }

    public void assemblyWrite(int address, String value) {
        assemblyMemory[address] = value;
    }

    public int getCurNumOfInstructions() {
        return curNumOfInstructions;
    }

    public void setCurNumOfInstructions(int NumOfInstructions) {
        curNumOfInstructions = NumOfInstructions;
    }

    public void printMemory() {
        System.out.println("Main Memory:");
        System.out.println("Instructions:");
        for (int i = instructionsStart; i <= instructionsEnd; i++) {
            System.out.println("Address " + i + ": " + Parser.parseMemory(mainMemory[i]));
        }
        System.out.println("Data:");
        for (int i = dataStart; i <= dataEnd; i++) {
            System.out.println("Address " + i + ": " + Parser.parseMemory(mainMemory[i]));
        }
        System.out.println("--------------------End of Main Memory--------------------");

    }


    public String[] getAssemblyMemory() {
        return assemblyMemory;
    }
}
