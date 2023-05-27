package control;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;
import parser.Parser;
import stages.*;

import java.io.FileNotFoundException;

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
                IF.getIFInstance().fetchInstruction(RegisterFile.getRegisterFileInstance().getPC());
                if (curCycle >= 2) {
                    if (curCycle >= 7) {
                        WB.getWBInstance().writeBack();
                        WBFlag = true;
                    }
                    ID.getIDInstance().decodeInstruction();
                }
                if (curCycle >= 4) {

                    EX.getEXInstance().executeInstruction();

                    if (controlUnitInstance.branchFlag) {
                        PipelineRegisters.flush();
                    }
                }
                if (!WBFlag && curCycle >= 7) {
                    WB.getWBInstance().writeBack();
                }
            } else {
                if (curCycle >= 6 && curCycle % 2 == 0) {
                    MEM.getMEMInstance().MemoryManipulation();
                }
            }

            WBFlag = false;

            System.out.println("Clock Cycle: " + curCycle);
            System.out.println("-----------------");
            if (curCycle % 2 == 1) {
                IF.getIFInstance().print();
                if (curCycle >= 2) {
                    ID.getIDInstance().print();
                }
                if (curCycle >= 4) {
                    EX.getEXInstance().print();
                }
                if (curCycle >= 7) {
                    WB.getWBInstance().print();
                }
            } else if (curCycle >= 6) {
                MEM.getMEMInstance().print();
            }
            PipelineRegisters.getPipelineRegisterInstance().propagateData(curCycle);

            if (curCycle % 2 == 1) {
                if (MainMemory.getMainMemoryInstance().assemblyRead(RegisterFile.getRegisterFileInstance().getPC() - 1) == null)
                    counter++;
                else
                    counter = 0;
            }

            curCycle++;
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
