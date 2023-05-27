package stages;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

import java.util.HashMap;

public class WB {

    private static final WB wbInstance = new WB();

    public static WB getWBInstance() {
        return wbInstance;
    }

    public void writeBack() {

        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        if (MEM_WB.getOrDefault("availableRight", 0) == 0) {
            return;
        }

        int opcode = MEM_WB.getOrDefault("opcodeRight", 0);
        int r1 = MEM_WB.getOrDefault("r1Right", 0);
        int r2 = MEM_WB.getOrDefault("r2Right", 0);
        int r3 = MEM_WB.getOrDefault("r3Right", 0);
        int r1Content = MEM_WB.getOrDefault("r1ContentRight", 0);
        int regWrite = MEM_WB.getOrDefault("regWriteRight", 0);
        int ALUResult = MEM_WB.getOrDefault("ALUResultRight", 0);
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
            System.out.println("------------------------------------------------------------");
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in WB stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("------------------------------------------------------------");
        System.out.println("Outputs: ");
        printOutput();
        System.out.println("------------------------------------------------------------");

    }

    public void printInput() {

        System.out.println("Inputs: ");
        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        int opcode = MEM_WB.getOrDefault("opcodeRight", 0);
        int r1 = MEM_WB.getOrDefault("r1Right", 0);
        int r2 = MEM_WB.getOrDefault("r2Right", 0);
        int r3 = MEM_WB.getOrDefault("r3Right", 0);
        int r1Content = MEM_WB.getOrDefault("r1ContentRight", 0);
        int regWrite = MEM_WB.getOrDefault("regWriteRight", 0);
        int ALUResult = MEM_WB.getOrDefault("ALUResultRight", 0);

        StringBuilder sb = new StringBuilder();
        sb.append("Opcode: ");
        sb.append("0".repeat(Math.max(0, 4 - Integer.toBinaryString(opcode).length())));
        sb.append(Integer.toBinaryString(opcode));

        sb.append("\nR1: ");
        sb.append("0".repeat(Math.max(0, 5 - Integer.toBinaryString(r1).length())));
        sb.append(Integer.toBinaryString(r1));

        sb.append("\nR2: ");
        sb.append("0".repeat(Math.max(0, 5 - Integer.toBinaryString(r2).length())));
        sb.append(Integer.toBinaryString(r2));

        sb.append("\nR3: ");
        sb.append("0".repeat(Math.max(0, 5 - Integer.toBinaryString(r3).length())));
        sb.append(Integer.toBinaryString(r3));

        sb.append("\nR1 Content: ");
        sb.append(r1Content);

        sb.append("\nRegister Write: ");
        sb.append(regWrite);

        sb.append("\nALU Result: ");
        sb.append(ALUResult);

        System.out.println(sb);

    }

    public void printOutput() {

        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        System.out.println("None");

        if (MEM_WB.getOrDefault("regWriteRight", 0) == 1) {
            System.out.println("------------------------------------------------------------");
            if (MEM_WB.getOrDefault("opcodeRight", 0) == 10) {
                System.out.println("Register " + MEM_WB.get("r1Right") + " is written with value " + MEM_WB.get("r1ContentRight"));
            }
            else {
                System.out.println("Register " + MEM_WB.get("r1Right") + " is written with value " + MEM_WB.get("ALUResultRight"));
            }
        }

    }

}
