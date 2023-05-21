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

    private ControlUnit() {
        try {
            Parser.readAssemblyFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        clockCycle = 1;
        int n = MainMemory.getMainMemoryInstance().getCurNumOfInstructions();
        numOfCycles = 7 + ((n - 1) * 2);
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // TODO code application logic here

        int curCycle = controlUnitInstance.clockCycle;
        int numOfCycles = controlUnitInstance.numOfCycles;
        boolean WBFlag = false;
        while (curCycle <= numOfCycles) {
            if (curCycle % 2 == 1 && curCycle <= numOfCycles - 6) {
                IF.getIFInstance().fetchInstruction(RegisterFile.getRegisterFileInstance().getPC());
            }
            if (curCycle % 2 == 1 && curCycle >= 2 && curCycle <= numOfCycles - 4) {
                if (curCycle >= 7) {
                    WB.getWBInstance().writeBack();
                    WBFlag = true;
                }
                ID.getIDInstance().decodeInstruction();
            }
            if (curCycle % 2 == 1 && curCycle >= 4 && curCycle <= numOfCycles - 2) {
                EX.getEXInstance().Execute();

                int opcode = PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("opcodeRight", 0);
                int ALUResult = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM().getOrDefault("ALUResultLeft", 0);

                if (opcode == 4) {
                    if (ALUResult != 0) {
                        int newPC = PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("pcRight", 0) + 1 + PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("immediateRight");
                        RegisterFile.getRegisterFileInstance().setPC(newPC);
                        PipelineRegisters.flush();
                    }
                } else if (opcode == 7) {
                    int newPC = ALUResult;
                    RegisterFile.getRegisterFileInstance().setPC(newPC);
                    PipelineRegisters.flush();

                }
            }
            if (curCycle >= 6 && curCycle % 2 == 0) {
                MEM.getMEMInstance().MemoryManipulation();
            }
            if (!WBFlag && curCycle >= 7 && curCycle % 2 == 1) {
                WB.getWBInstance().writeBack();
            }
            WBFlag = false;
            PipelineRegisters.getPipelineRegisterInstance().propagateData(curCycle);
            System.out.println("Clock Cycle: " + curCycle);
            if (curCycle % 2 == 1 && curCycle <= numOfCycles - 6) {
                IF.getIFInstance().print();
            }
            if (curCycle % 2 == 1 && curCycle >= 2 && curCycle <= numOfCycles - 4) {
                ID.getIDInstance().print();
            }
            if (curCycle % 2 == 1 && curCycle >= 4 && curCycle <= numOfCycles - 2) {
                EX.getEXInstance().print();
            }
            if (curCycle >= 6 && curCycle % 2 == 0) {
                MEM.getMEMInstance().print();
            }
            if (curCycle >= 7 && curCycle % 2 == 1) {
                WB.getWBInstance().print();
            }
            curCycle++;
        }


        MainMemory.getMainMemoryInstance().printMemory();
        RegisterFile.getRegisterFileInstance().printRegisters();

    }

}
