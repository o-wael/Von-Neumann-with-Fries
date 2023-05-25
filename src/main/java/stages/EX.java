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
        int regWrite = ID_EX.getOrDefault("regWriteRight", 0);
        int ALUResult = 0;
        ControlUnit.getControlUnitInstance().setBranchFlag(false);
        if (ID_EX.getOrDefault("availableRight", 0) == 0) {
            return;
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

        System.out.println("Opcode: " + Integer.toBinaryString(opcode) + "\nR1: " + Integer.toBinaryString(r1) + "\nR2 Content: " + r2Content + "\nR3 Content: " + r3Content + "\nShamt: " + Integer.toBinaryString(shamt) + "\nImmediate: " + Integer.toBinaryString(immediate) + "\nAddress: " + Integer.toBinaryString(address));

    }

    public void printOutput() {

        HashMap<String, Integer> EX_MEM = PipelineRegisters.getPipelineRegisterInstance().getEX_MEM();

        System.out.println("Opcode: " + EX_MEM.get("opcodeLeft"));
        System.out.println("R1: " + EX_MEM.get("r1Left"));
        System.out.println("R1 Content: " + EX_MEM.get("r1ContentLeft"));
        System.out.println("Immediate: " + EX_MEM.get("immediateLeft"));
        System.out.println("Register Write: " + EX_MEM.get("regWriteLeft"));
        System.out.println("ALU Result: " + EX_MEM.get("ALUResultLeft"));
        System.out.println("available: " + PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("availableRight"));
    }

}
