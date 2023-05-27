package stages;

import control.ControlUnit;
import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

import java.util.HashMap;

public class EX {

    private static final EX exInstance = new EX();

    public static EX getEXInstance() {
        return exInstance;
    }

    public void executeInstruction() {

        HashMap<String, Integer> ID_EX = PipelineRegisters.getPipelineRegisterInstance().getID_EX();
        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();
        HashMap<String, Integer> MEM_WB = PipelineRegisters.getPipelineRegisterInstance().getMEM_WB();
//        System.out.println("InExecute---------------->availableRightInID_EX = " + ID_EX.getOrDefault("availableRight", 0));

        EX_MEM.put("availableLeft", ID_EX.getOrDefault("availableRight", 0));
        int opcode = ID_EX.getOrDefault("opcodeRight", 0);
        int r1Content = ID_EX.getOrDefault("r1ContentRight", 0);
        int r2Content = ID_EX.getOrDefault("r2ContentRight", 0);
        int r3Content = ID_EX.getOrDefault("r3ContentRight", 0);
        int shamt = ID_EX.getOrDefault("shamtRight", 0);
        int immediate = ID_EX.getOrDefault("immediateRight", 0);
        int address = ID_EX.getOrDefault("addressRight", 0);
        int r1 = ID_EX.getOrDefault("r1Right", 0);
        int r2 = ID_EX.getOrDefault("r2Right", 0);
        int r3 = ID_EX.getOrDefault("r3Right", 0);
        int regWrite = ID_EX.getOrDefault("regWriteRight", 0);
        int ALUResult = 0;
        ControlUnit.getControlUnitInstance().setBranchFlag(false);
        if (ID_EX.getOrDefault("availableRight", 0) == 0) {
            return;
        }
        int op = MEM_WB.getOrDefault("opcodeRight", 0);
        if(MEM_WB.getOrDefault("r1Right", 0) !=0) {
            if (op == 0 || op == 1 || op == 2 || op == 3 || op == 5 || op == 6 || op == 8 || op == 9) {
                if (opcode == 0 || opcode == 1) {
                    if (r2 == MEM_WB.getOrDefault("r1Right", 0))
                        r2Content = MEM_WB.getOrDefault("ALUResultRight", 0);
                    if (r3 == MEM_WB.getOrDefault("r1Right", 0))
                        r3Content = MEM_WB.getOrDefault("ALUResultRight", 0);
                } else if (opcode == 2 || opcode == 3 || opcode == 5 || opcode == 6 || opcode == 8 || opcode == 9 || opcode == 10) {
                    if (r2 == MEM_WB.getOrDefault("r1Right", 0))
                        r2Content = MEM_WB.getOrDefault("ALUResultRight", 0);
                } else if (opcode == 4 || opcode == 11) {
                    if (r1 == MEM_WB.getOrDefault("r1Right", 0))
                        r1Content = MEM_WB.getOrDefault("ALUResultRight", 0);
                    if (r2 == MEM_WB.getOrDefault("r1Right", 0))
                        r2Content = MEM_WB.getOrDefault("ALUResultRight", 0);
                }
            } else if (op == 10) {
                if (opcode == 0 || opcode == 1) {
                    if (r2 == MEM_WB.getOrDefault("r1Right", 0))
                        r2Content = MEM_WB.getOrDefault("r1ContentRight", 0);
                    if (r3 == MEM_WB.getOrDefault("r1Right", 0))
                        r3Content = MEM_WB.getOrDefault("r1ContentRight", 0);
                } else if (opcode == 2 || opcode == 3 || opcode == 5 || opcode == 6 || opcode == 8 || opcode == 9 || opcode == 10) {
                    if (r2 == MEM_WB.getOrDefault("r1Right", 0))
                        r2Content = MEM_WB.getOrDefault("r1ContentRight", 0);
                } else if (opcode == 4 || opcode == 11) {
                    if (r1 == MEM_WB.getOrDefault("r1Right", 0))
                        r1Content = MEM_WB.getOrDefault("r1ContentRight", 0);
                    if (r2 == MEM_WB.getOrDefault("r1Right", 0))
                        r2Content = MEM_WB.getOrDefault("r1ContentRight", 0);
                }
            }

        }

        switch (opcode) {
            case 0 ->
                //add
                    ALUResult = r2Content + r3Content;
            case 1 ->
                //sub
                    ALUResult = r2Content - r3Content;
            case 2 ->
                //muli
                    ALUResult = r2Content * immediate;
            case 3 -> {
                //addi
                ALUResult = r2Content + immediate;
            }
            case 4 -> {
                //bne
                ALUResult = r1Content - r2Content;
                if (ALUResult != 0) {
                    int newPC = PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("pcRight", 0) + 1 + immediate;
                    RegisterFile.getRegisterFileInstance().setPC(newPC);
                    ControlUnit.getControlUnitInstance().setBranchFlag(true);
                }
            }
            case 5 ->
                //andi
                    ALUResult = r2Content & immediate;
            case 6 ->
                //ori
                    ALUResult = r2Content | immediate;
            case 7 -> {
                //j
                int oldPC = PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("pcRight", 0);
//                System.out.println("Address--------------------------->" + address);
                //concatenate bits 31:28 of oldPC with bits 27:0 of address
                ALUResult = (oldPC & 0b11110000000000000000000000000000) | address;
                RegisterFile.getRegisterFileInstance().setPC(ALUResult);
                ControlUnit.getControlUnitInstance().setBranchFlag(true);
            }
            case 8 ->
                //sll
                    ALUResult = r2Content << shamt;
            case 9 ->
                //srl
                    ALUResult = r2Content >>> shamt;
            case 10, 11 -> {
                //lw & sw
                ALUResult = r2Content + immediate;
                if (ALUResult < 1024) {
                    ALUResult += 1024;
                }
            }

        }


        EX_MEM.put("opcodeLeft", opcode);
        EX_MEM.put("r1Left", r1);
        EX_MEM.put("r2Left", r2);
        EX_MEM.put("r3Left", r3);
        EX_MEM.put("r1ContentLeft", r1Content);
        EX_MEM.put("immediateLeft", immediate);
        EX_MEM.put("regWriteLeft", regWrite);
        EX_MEM.put("ALUResultLeft", ALUResult);
        EX_MEM.put("pcLeft", ID_EX.getOrDefault("pcRight", 0));


    }





    public void print() {
        if (PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("availableRight", 0) == 0) {
            System.out.println("No instruction is executed in EX stage");
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getID_EX().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in EX stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("--------------------------------------------------");
        System.out.print("Outputs: ");
        printOutput();
        System.out.println("--------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs: ");
        HashMap<String, Integer> ID_EX = PipelineRegisters.getPipelineRegisterInstance().getID_EX();

        int opcode = ID_EX.getOrDefault("opcodeRight", 0);
        int r2Content = ID_EX.getOrDefault("r2ContentRight", 0);
        int r3Content = ID_EX.getOrDefault("r3ContentRight", 0);
        int shamt = ID_EX.getOrDefault("shamtRight", 0);
        int immediate = ID_EX.getOrDefault("immediateRight", 0);
        int address = ID_EX.getOrDefault("addressRight", 0);
        int r1 = ID_EX.getOrDefault("r1Right", 0);
        int r2 = ID_EX.getOrDefault("r2Right", 0);
        int r3 = ID_EX.getOrDefault("r3Right", 0);

        System.out.println("Opcode: " + Integer.toBinaryString(opcode) + "\nR1: " + Integer.toBinaryString(r1) + "\nR2: " + Integer.toBinaryString(r2) + "\nR3: " + Integer.toBinaryString(r3) + "\nR2 Content: " + r2Content + "\nR3 Content: " + r3Content + "\nShamt: " + Integer.toBinaryString(shamt) + "\nImmediate: " + Integer.toBinaryString(immediate) + "\nAddress: " + Integer.toBinaryString(address));

    }

    public void printOutput() {

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();

        System.out.println("Opcode: " + EX_MEM.get("opcodeLeft"));
        System.out.println("R1: " + EX_MEM.get("r1Left"));
        System.out.println("R2: " + EX_MEM.get("r2Left"));
        System.out.println("R3: " + EX_MEM.get("r3Left"));
        System.out.println("R1 Content: " + EX_MEM.get("r1ContentLeft"));
        System.out.println("Immediate: " + EX_MEM.get("immediateLeft"));
        System.out.println("Register Write: " + EX_MEM.get("regWriteLeft"));
        System.out.println("ALU Result: " + EX_MEM.get("ALUResultLeft"));
        System.out.println("available: " + PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("availableRight"));
    }

}
