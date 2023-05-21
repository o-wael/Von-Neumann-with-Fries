package memory;

public class RegisterFile {

    private static final int numOfRegisters = 32;
    private static final RegisterFile registerFileInstance = new RegisterFile();
    private int[] registerFile;
    private int pc;

    private RegisterFile() {
        registerFile = new int[numOfRegisters];
        pc = 0;
    }

    public static RegisterFile getRegisterFileInstance() {
        return registerFileInstance;
    }

    public void writeToRegister(int registerNumber, int value) {
        if (registerNumber == 0) {
            return;
        }
        registerFileInstance.registerFile[registerNumber] = value;
    }

    public int readFromRegister(int registerNumber) {
        return registerFileInstance.registerFile[registerNumber];
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int pc) {
        this.pc = pc;
    }

    public void printRegisters() {
        System.out.println("Register File:");
        for (int i = 0; i < numOfRegisters; i++) {
            System.out.println("R" + i + ": " + registerFile[i]);
        }
        System.out.println("PC: " + pc);
        System.out.println("--------------------End of Register File--------------------");
    }
}
