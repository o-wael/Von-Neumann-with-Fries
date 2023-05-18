package stages;

import memory.MainMemory;
import memory.PipelineRegisters;

public class ID {

    public static void decodeInstruction(int pc) {
        int newInstruction = MainMemory.getMainMemoryInstance().memRead(pc);
        PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("instructionWrite", newInstruction);
    }

}
