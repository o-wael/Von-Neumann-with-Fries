package stages;

import memory.MainMemory;
import memory.PipelineRegisters;

import java.util.HashMap;

public class MEM {

    private static final MEM memInstance = new MEM();

    public static void propagate(int clockCycle) {

        //TODO

    }

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

        int opcode = EX_MEM.get("opcodeRight");
        int r1 = EX_MEM.get("r1Right");
        int r2 = EX_MEM.get("r2Right");
        int r3 = EX_MEM.get("r3Right");
        int r1Content = EX_MEM.get("r1ContentRight");
        int immediate = EX_MEM.get("immediateRight");
        int regWrite = EX_MEM.get("regWriteRight");
        int ALUResult = EX_MEM.get("ALUResultRight");

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
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in MEM stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("--------------------------------------------------");
        System.out.println("Outputs: ");
        printOutput();
        System.out.println("--------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs: ");

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();

        System.out.println("Opcode: " + EX_MEM.get("opcodeRight"));
        System.out.println("R1: " + EX_MEM.get("r1Right"));
        System.out.println("R2: " + EX_MEM.get("r2Right"));
        System.out.println("R3: " + EX_MEM.get("r3Right"));
        System.out.println("R1 Content: " + EX_MEM.get("r1ContentRight"));
        System.out.println("Immediate: " + EX_MEM.get("immediateRight"));
        System.out.println("Register Write: " + EX_MEM.get("regWriteRight"));
        System.out.println("ALU Result: " + EX_MEM.get("ALUResultRight"));
    }

    public void printOutput() {

        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        System.out.println("Opcode: " + MEM_WB.get("opcodeLeft"));
        System.out.println("R1: " + MEM_WB.get("r1Left"));
        System.out.println("R2: " + MEM_WB.get("r2Left"));
        System.out.println("R3: " + MEM_WB.get("r3Left"));
        System.out.println("R1 Content: " + MEM_WB.get("r1ContentLeft"));
        System.out.println("Register Write: " + MEM_WB.get("regWriteLeft"));
        System.out.println("ALU Result: " + MEM_WB.get("ALUResultLeft"));
        System.out.println("available: " + PipelineRegisters.getPipelineRegisterInstance().getEX_MEM().get("availableRight"));

    }

}
