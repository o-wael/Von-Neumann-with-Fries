package stages;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

public class IF {

    private int pc;
    private int instructionNumber;

    private static final IF ifInstance = new IF();

    public void fetchInstruction(int pc) {
        int newInstruction = MainMemory.getMainMemoryInstance().memRead(pc);
        PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("instructionLeft", newInstruction);
        this.pc = pc;
        RegisterFile.getRegisterFileInstance().setPC(pc + 1);
    }

    public static void propagate(int clockCycle) {

    //TODO

    }

    public void print() {
        System.out.println("Instruction Being Executed in IF stage: " + instructionNumber);
        System.out.println("Inputs:\nPC: " + pc);
        System.out.println("Outputs:\nInstruction: " + PipelineRegisters.getPipelineRegisterInstance().getIF_ID().get("instructionLeft"));
    }

    public static IF getIFInstance() {
        return ifInstance;
    }

}
