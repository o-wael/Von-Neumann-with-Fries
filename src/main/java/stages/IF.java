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
        if (MainMemory.getMainMemoryInstance().assemblyRead(pc) == null) {
            PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("availableLeft", 0);
        } else {
            PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("availableLeft", 1);
        }
        this.pc = pc;
        if (pc <= 1023)
            RegisterFile.getRegisterFileInstance().setPC(pc + 1);
    }

    public void print() {
        if (MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1) == null) {
            System.out.println("No instruction is executed in IF stage");
            System.out.println("------------------------------------------------------------");
            return;
        }
        System.out.println("Instruction Being Executed in IF stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("------------------------------------------------------------");
        System.out.print("Outputs: ");
        printOutput();
        System.out.println("------------------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs:\nPC: " + pc);
    }

    public void printOutput() {
        StringBuilder sb = new StringBuilder();
        sb.append("Instruction: ");
        sb.append("0".repeat(Math.max(0, 32 - Integer.toBinaryString(MainMemory.getMainMemoryInstance().memRead(pc)).length())));
        sb.append(Integer.toBinaryString(MainMemory.getMainMemoryInstance().memRead(pc)));
        System.out.println(sb);
    }

}
