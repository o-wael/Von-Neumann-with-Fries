package control;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;
import parser.Parser;
import stages.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class ControlUnit {

    private static final ControlUnit controlUnitInstance = new ControlUnit();
    int clockCycle;
    int numOfCycles;
    boolean branchFlag;

    private ControlUnit() {
        try {
            Parser.readAssemblyFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        clockCycle = 1;
        int n = MainMemory.getMainMemoryInstance().getCurNumOfInstructions();
        numOfCycles = 7 + ((n - 1) * 2);
        setBranchFlag(false);
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // TODO code application logic here

        int curCycle = controlUnitInstance.clockCycle;
        int numOfCycles = controlUnitInstance.numOfCycles;
        boolean WBFlag = false;
        ControlUnit.getControlUnitInstance().setBranchFlag(false);
        int counter = 0;
        while (counter < 3) {
            if (curCycle % 2 == 1) {
//                if (MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC()) != null) {

                IF.getIFInstance().fetchInstruction(RegisterFile.getRegisterFileInstance().getPC());
//                }
//                else {
//                    RegisterFile.getRegisterFileInstance().setPC(RegisterFile.getRegisterFileInstance().getPC());
//                }
                if (curCycle >= 2) {
//                    if (MainMemory.getMainMemoryInstance().assemblyRead(PipelineRegisters.getPipelineRegisterInstance().getIF_ID().get("pcRight")) != null) {
                        if (curCycle >= 7) {
                            if(curCycle == 9){
                                System.out.println("");
                            }

                            WB.getWBInstance().writeBack();
                            WBFlag = true;
                        }
                        ID.getIDInstance().decodeInstruction();
//                    }
                }
                if (curCycle >= 4) {

//                    if (MainMemory.getMainMemoryInstance().assemblyRead(PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("pcRight")) != null) {
                        EX.getEXInstance().executeInstruction();

                        int opcode = PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("opcodeRight", 0);

                        if (controlUnitInstance.branchFlag) {
                            System.out.println(PipelineRegisters.getPipelineRegisterInstance().getIF_ID().get("availableLeft"));
                            System.out.println(PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("availableLeft"));
                            PipelineRegisters.flush();
                        }
//                    }
                }
                if (!WBFlag && curCycle >= 7) {
//                    if (PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("availableRight") == 0)

                    WB.getWBInstance().writeBack();
                }
            }
            else {
                if (curCycle >= 6 && curCycle % 2 == 0) {
                    MEM.getMEMInstance().MemoryManipulation();
                }
            }
            WBFlag = false;
            if (curCycle == 19) {
                System.out.println("");
            }
            boolean fetchDoneFlag = (MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1) == null) && PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("opcodeRight") != 4 && PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("opcodeRight") != 7;
            String x = MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1);
            int pc = RegisterFile.getRegisterFileInstance().getPC();

            boolean decodeDoneFlag = curCycle > 2 && (PipelineRegisters.getPipelineRegisterInstance().getIF_ID().get("availableRight") == 0);
            boolean executeDoneFlag = curCycle > 4 && (PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("availableRight") == 0);
            boolean memoryDoneFlag = curCycle > 5 && (PipelineRegisters.getPipelineRegisterInstance().getEX_MEM().get("availableRight") == 0);
            boolean writeBackDoneFlag = curCycle > 6 && (PipelineRegisters.getPipelineRegisterInstance().getMEM_WB().get("availableRight") == 0);
//            if (fetchDoneFlag && decodeDoneFlag && executeDoneFlag && memoryDoneFlag && writeBackDoneFlag)
//                break;

//            if (writeBackDoneFlag)
//                break;

            System.out.println("Clock Cycle: " + curCycle);
            if (curCycle % 2 == 1) {
//                if (!fetchDoneFlag) {
                    IF.getIFInstance().print();
//                }
                if (curCycle >= 2) {
                    ID.getIDInstance().print();
                }
                if (curCycle >= 4) {
                    EX.getEXInstance().print();
                }
                if (curCycle >= 7) {
                    WB.getWBInstance().print();
                }
            }
            else if (curCycle >= 6) {
                MEM.getMEMInstance().print();
            }
            PipelineRegisters.getPipelineRegisterInstance().propagateData(curCycle);

//            fetchDoneFlag = (MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1) == null) && PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("opcodeRight") != 4 && PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("opcodeRight") != 7 && PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("opcodeLeft") != 4 && PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("opcodeLeft") != 7;
//            x = MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1);
//            pc = RegisterFile.getRegisterFileInstance().getPC();

//            if (branchFlag) {
//                curCycle = (RegisterFile.getRegisterFileInstance().getPC() * 2) - 1;
//                branchFlag = false;
//            }
//            else

            if (curCycle % 2 == 1) {
                if (MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1) == null)
                    counter++;
                else
                    counter = 0;
            }

            curCycle++;
//            RegisterFile.getRegisterFileInstance().printRegisters();


//            if (curCycle >= 7 && MainMemory.getMainMemoryInstance().assemblyRead(PipelineRegisters.getPipelineRegisterInstance().getMEM_WB().get("pcRight")) == null)
//                break;
        }

        MainMemory.getMainMemoryInstance().printMemory();
        RegisterFile.getRegisterFileInstance().printRegisters();

    }

    public static ControlUnit getControlUnitInstance() {
        return controlUnitInstance;
    }

    public void setBranchFlag(boolean value) {
        this.branchFlag = value;
    }

}
