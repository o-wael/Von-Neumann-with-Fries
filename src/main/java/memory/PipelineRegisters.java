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

    }

    public static void propagate_ID_EX() {

    }

    public static void propagate_EX_MEM() {

    }

    public static void propagate_MEM_WB() {

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

        int n = 7;
        int numOfCycles = 7 + ((n - 1) * 2);


        if (clockCycle % 2 == 1) {
            propagate_IF_ID();
            propagate_ID_EX();
            propagate_EX_MEM();
            if (clockCycle > 6 && MEM_WB.getOrDefault("regWrite", 0) == 1) {
                RegisterFile.getRegisterFileInstance().writeToRegister(MEM_WB.getOrDefault("rd", 0), MEM_WB.getOrDefault("rdValue", 0));
            }
        } else {

            propagate_MEM_WB();
        }

    }
}
