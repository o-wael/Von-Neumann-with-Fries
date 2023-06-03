package memory;

import java.util.HashMap;

public class PipelineRegisters {

    private static final PipelineRegisters pipelineRegisterInstance = new PipelineRegisters();
    private HashMap<String, Integer> IF_ID;
    private HashMap<String, Integer> ID_EX;
    private HashMap<String, Integer> EX_MEM;
    private HashMap<String, Integer> MEM_WB;

    private PipelineRegisters() {
        IF_ID = new HashMap<>();
        ID_EX = new HashMap<>();
        EX_MEM = new HashMap<>();
        MEM_WB = new HashMap<>();
    }

    public static PipelineRegisters getPipelineRegisterInstance() {
        return pipelineRegisterInstance;
    }

    public static void propagate_IF_ID() {

        HashMap<String, Integer> IF_ID = PipelineRegisters.getPipelineRegisterInstance().getIF_ID();

        IF_ID.put("instructionRight", IF_ID.getOrDefault("instructionLeft", 0));
        IF_ID.put("pcRight", IF_ID.getOrDefault("pcLeft", 0));
        IF_ID.put("availableRight", IF_ID.getOrDefault("availableLeft", 0));


    }

    public static void propagate_ID_EX() {

        HashMap<String, Integer> ID_EX = PipelineRegisters.getPipelineRegisterInstance().getID_EX();

        ID_EX.put("opcodeRight", ID_EX.getOrDefault("opcodeLeft", 0));
        ID_EX.put("r1Right", ID_EX.getOrDefault("r1Left", 0));
        ID_EX.put("r2Right", ID_EX.getOrDefault("r2Left", 0));
        ID_EX.put("r3Right", ID_EX.getOrDefault("r3Left", 0));
        ID_EX.put("r1ContentRight", ID_EX.getOrDefault("r1ContentLeft", 0));
        ID_EX.put("r2ContentRight", ID_EX.getOrDefault("r2ContentLeft", 0));
        ID_EX.put("r3ContentRight", ID_EX.getOrDefault("r3ContentLeft", 0));
        ID_EX.put("shamtRight", ID_EX.getOrDefault("shamtLeft", 0));
        ID_EX.put("regWriteRight", ID_EX.getOrDefault("regWriteLeft", 0));
        ID_EX.put("immediateRight", ID_EX.getOrDefault("immediateLeft", 0));
        ID_EX.put("addressRight", ID_EX.getOrDefault("addressLeft", 0));
        ID_EX.put("pcRight", ID_EX.getOrDefault("pcLeft", 0));
        ID_EX.put("availableRight", ID_EX.getOrDefault("availableLeft", 0));


    }

    public static void propagate_EX_MEM() {

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();

        EX_MEM.put("opcodeRight", EX_MEM.getOrDefault("opcodeLeft", 0));
        EX_MEM.put("r1Right", EX_MEM.getOrDefault("r1Left", 0));
        EX_MEM.put("r2Right", EX_MEM.getOrDefault("r2Left", 0));
        EX_MEM.put("r3Right", EX_MEM.getOrDefault("r3Left", 0));
        EX_MEM.put("r1ContentRight", EX_MEM.getOrDefault("r1ContentLeft", 0));
        EX_MEM.put("immediateRight", EX_MEM.getOrDefault("immediateLeft", 0));
        EX_MEM.put("regWriteRight", EX_MEM.getOrDefault("regWriteLeft", 0));
        EX_MEM.put("ALUResultRight", EX_MEM.getOrDefault("ALUResultLeft", 0));
        EX_MEM.put("pcRight", EX_MEM.getOrDefault("pcLeft", 0));
        EX_MEM.put("availableRight", EX_MEM.getOrDefault("availableLeft", 0));


    }

    public static void propagate_MEM_WB() {

        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();

        MEM_WB.put("opcodeRight", MEM_WB.getOrDefault("opcodeLeft", 0));
        MEM_WB.put("r1Right", MEM_WB.getOrDefault("r1Left", 0));
        MEM_WB.put("r2Right", MEM_WB.getOrDefault("r2Left", 0));
        MEM_WB.put("r3Right", MEM_WB.getOrDefault("r3Left", 0));
        MEM_WB.put("r1ContentRight", MEM_WB.getOrDefault("r1ContentLeft", 0));
        MEM_WB.put("regWriteRight", MEM_WB.getOrDefault("regWriteLeft", 0));
        MEM_WB.put("ALUResultRight", MEM_WB.getOrDefault("ALUResultLeft", 0));
        MEM_WB.put("pcRight", MEM_WB.getOrDefault("pcLeft", 0));
        MEM_WB.put("availableRight", MEM_WB.getOrDefault("availableLeft", 0));

    }

    public static void flush() {
        PipelineRegisters.getPipelineRegisterInstance().getIF_ID().put("availableLeft", 0);
        PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("availableLeft", 0);
    }

    public HashMap<String, Integer> getIF_ID() {
        return IF_ID;
    }

    public HashMap<String, Integer> getID_EX() {
        return ID_EX;
    }

    public HashMap<String, Integer> getEX_MEM() {
        return EX_MEM;
    }

    public HashMap<String, Integer> getMEM_WB() {
        return MEM_WB;
    }

    public void propagateData(int clockCycle) {

        if (clockCycle % 2 == 1) {
            propagate_IF_ID();
            if (clockCycle >= 3) {
                propagate_ID_EX();
            }
            if (clockCycle >= 5) {
                propagate_EX_MEM();
            }
        } else {
            if (clockCycle >= 6)
                propagate_MEM_WB();
        }

    }
}
