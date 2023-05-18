package memory;

public class RegisterFile {

    private int[] registerFile;
    private static final int numOfRegisters = 32;
    private int pc;
    private static final RegisterFile registerFileInstance = new RegisterFile();

    private RegisterFile() {
        registerFile = new int[numOfRegisters];
        pc = 0;
    }

    public void setPC(int pc) {
        this.pc = pc;
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
}
