package stages;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

import java.util.HashMap;

public class WB {

    private static final WB wbInstance = new WB();
    private int opcode;
    private int r1;
    private int r1Content;
    private int regWrite;
    private int ALUResult;

    public static WB getWBInstance() {
        return wbInstance;
    }

    public void writeBack() {

        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        if (MEM_WB.getOrDefault("availableRight", 0) == 0) {
            return;
        }

        int opcode = MEM_WB.get("opcodeRight");
        int r1 = MEM_WB.get("r1Right");
        int r1Content = MEM_WB.get("r1ContentRight");
        int regWrite = MEM_WB.get("regWriteRight");
        int ALUResult = MEM_WB.get("ALUResultRight");

        if (regWrite == 1) {
            if (opcode == 0 || opcode == 1 || opcode == 2 || opcode == 3 || opcode == 5 || opcode == 6 || opcode == 8 || opcode == 9) {
                RegisterFile.getRegisterFileInstance().writeToRegister(r1, ALUResult);
            } else if (opcode == 10) {
                RegisterFile.getRegisterFileInstance().writeToRegister(r1, r1Content);
            }
        }

    }

    public void print() {
        if (PipelineRegisters.getPipelineRegisterInstance().getMEM_WB().getOrDefault("availableRight", 0) == 0) {
            System.out.println("No instruction is executed in WB stage");
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in WB stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("--------------------------------------------------");
        System.out.println("Outputs: ");
        printOutput();
        System.out.println("--------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs: ");
        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        System.out.println("Opcode: " + MEM_WB.get("opcodeRight"));
        System.out.println("R1: " + MEM_WB.get("r1Right"));
        System.out.println("R1 Content: " + MEM_WB.get("r1ContentRight"));
        System.out.println("Register Write: " + MEM_WB.get("regWriteRight"));
        System.out.println("ALU Result: " + MEM_WB.get("ALUResultRight"));
    }

    public void printOutput() {
        //TODO To be reviewed
        System.out.println("None");
    }

}
