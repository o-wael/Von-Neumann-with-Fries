package memory;

public class MainMemory {

    private static final MainMemory mainMemoryInstance = new MainMemory();

    private int[] mainMemory;
    private static final int memorySize = 2048;
    private static final int instructionsStart = 0;
    private static final int instructionsEnd = 1023;
    private static final int dataStart = 1024;
    private static final int dataEnd = 2047;

    private MainMemory() {
        mainMemory = new int[memorySize];
    }

    public static MainMemory getMainMemoryInstance() {
        return mainMemoryInstance;
    }

    public int memRead(int address){
        return mainMemory[address];
    }

    public void memWrite(int address, int value) {
        mainMemory[address] = value;
    }


}
