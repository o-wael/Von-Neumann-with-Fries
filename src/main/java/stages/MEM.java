package stages;

import memory.MainMemory;
import memory.PipelineRegisters;

import java.util.HashMap;

public class MEM {

    private static final MEM memInstance = new MEM();

    public static MEM getMEMInstance() {
        return memInstance;
    }

    public void MemoryManipulation() {

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();
        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        MEM_WB.put("availableLeft", EX_MEM.getOrDefault("availableRight", 0));
        if (EX_MEM.getOrDefault("availableRight", 0) == 0) {
            return;
        }

        int opcode = EX_MEM.getOrDefault("opcodeRight", 0);
        int r1 = EX_MEM.getOrDefault("r1Right", 0);
        int r2 = EX_MEM.getOrDefault("r2Right", 0);
        int r3 = EX_MEM.getOrDefault("r3Right", 0);
        int r1Content = EX_MEM.getOrDefault("r1ContentRight", 0);
        int immediate = EX_MEM.getOrDefault("immediateRight", 0);
        int regWrite = EX_MEM.getOrDefault("regWriteRight", 0);
        int ALUResult = EX_MEM.getOrDefault("ALUResultRight", 0);

        if (opcode == 10) {
            r1Content = MainMemory.getMainMemoryInstance().memRead(ALUResult);
        } else if (opcode == 11) {
            MainMemory.getMainMemoryInstance().memWrite(ALUResult, r1Content);
        }


        MEM_WB.put("opcodeLeft", opcode);
        MEM_WB.put("r1Left", r1);
        MEM_WB.put("r2Left", r2);
        MEM_WB.put("r3Left", r3);
        MEM_WB.put("r1ContentLeft", r1Content);
        MEM_WB.put("regWriteLeft", regWrite);
        MEM_WB.put("ALUResultLeft", ALUResult);
        MEM_WB.put("pcLeft", EX_MEM.getOrDefault("pcRight", 0));


    }

    public void print() {
        if (PipelineRegisters.getPipelineRegisterInstance().getEX_MEM().getOrDefault("availableRight", 0) == 0) {
            System.out.println("No instruction is executed in MEM stage");
            System.out.println("------------------------------------------------------------");
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in MEM stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("------------------------------------------------------------");
        System.out.println("Outputs: ");
        printOutput();
        System.out.println("------------------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs: ");

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();

        int opcode = EX_MEM.getOrDefault("opcodeRight", 0);
        int r1 = EX_MEM.getOrDefault("r1Right", 0);
        int r2 = EX_MEM.getOrDefault("r2Right", 0);
        int r3 = EX_MEM.getOrDefault("r3Right", 0);
        int r1Content = EX_MEM.getOrDefault("r1ContentRight", 0);
        int immediate = EX_MEM.getOrDefault("immediateRight", 0);
        int regWrite = EX_MEM.getOrDefault("regWriteRight", 0);
        int ALUResult = EX_MEM.getOrDefault("ALUResultRight", 0);

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

        sb.append("\nImmediate: ");
        sb.append("0".repeat(Math.max(0, 18 - Integer.toBinaryString(immediate).length())));
        sb.append(Integer.toBinaryString(immediate));

        sb.append("\nRegWrite: ");
        sb.append(regWrite);

        sb.append("\nALU Result: ");
        sb.append(ALUResult);

        System.out.println(sb);
    }

    public void printOutput() {

        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        int opcode = MEM_WB.getOrDefault("opcodeLeft", 0);
        int r1 = MEM_WB.getOrDefault("r1Left", 0);
        int r2 = MEM_WB.getOrDefault("r2Left", 0);
        int r3 = MEM_WB.getOrDefault("r3Left", 0);
        int r1Content = MEM_WB.getOrDefault("r1ContentLeft", 0);
        int regWrite = MEM_WB.getOrDefault("regWriteLeft", 0);
        int ALUResult = MEM_WB.getOrDefault("ALUResultLeft", 0);

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

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();

        if (EX_MEM.getOrDefault("opcodeRight", 0) == 11){
            System.out.println("------------------------------------------------------------");
            System.out.println("Memory location " + EX_MEM.get("ALUResultRight") + " is written with value " + EX_MEM.get("r1ContentRight"));
        }

    }

}
