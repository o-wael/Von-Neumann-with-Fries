package stages;

import memory.MainMemory;
import memory.PipelineRegisters;

public class IF {

    public static void fetchInstruction(int pc) {
        int newInstruction = MainMemory.getMainMemoryInstance().memRead(pc);
        PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("instructionLeft", newInstruction);
    }

    public static void propagate(int clockCycle) {



    }

}
