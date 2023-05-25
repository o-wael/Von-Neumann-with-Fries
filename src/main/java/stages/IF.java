package stages;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

public class IF {

    private static final IF ifInstance = new IF();
    private int pc;

    public static IF getIFInstance() {
        return ifInstance;
    }

    public void fetchInstruction(int pc) {
        int newInstruction = MainMemory.getMainMemoryInstance().memRead(pc);
        PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("instructionLeft", newInstruction);
        PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("pcLeft", pc);
        System.out.println("in IF ---------------------------> " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        if (MainMemory.getMainMemoryInstance().assemblyRead(pc) == null) {
            PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("availableLeft", 0);
            System.out.println("I am kofta : " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        }
        else {
            PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("availableLeft", 1);
            System.out.println("I am here with assembly : " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        }
        this.pc = pc;
//        System.out.println("PCinFetch----------------------------->" + this.pc);
        if (pc <= 1023)
            RegisterFile.getRegisterFileInstance().setPC(pc + 1);
    }

    public void print() {
//        if (PipelineRegisters.getPipelineRegisterInstance().getIF_ID().get("availableLeft") == 0) {
//            System.out.println("No instruction is executed in IF stage");
//            return;
//        }
        System.out.println("Instruction Being Executed in IF stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("--------------------------------------------------");
        System.out.print("Outputs: ");
        printOutput();
        System.out.println("--------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs:\nPC: " + pc);
    }

    public void printOutput() {
        System.out.println("Instruction: " + Integer.toBinaryString(MainMemory.getMainMemoryInstance().memRead(pc)));
        System.out.println("available: " + PipelineRegisters.getPipelineRegisterInstance().getIF_ID().get("availableLeft"));
    }

}
