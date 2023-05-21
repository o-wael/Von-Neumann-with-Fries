package stages;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

import java.util.HashMap;

public class ID {

    private static final ID idInstance = new ID();

    public static ID getIDInstance() {
        return idInstance;
    }

    public void decodeInstruction() {

        if (PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("availableRight", 0) == 0)
            return;

        int instruction = PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0);

        int opcode = instruction >>> 28;
        int mask = 0;
        for (int i = 0; i < 5; i++)
            mask |= (1 << i);
        int r1 = (instruction >>> 23) & mask;
        int r2 = (instruction >>> 18) & mask;
        int r3 = (instruction >>> 13) & mask;
        for (int i = 5; i < 13; i++)
            mask |= (1 << i);
        int shamt = instruction & mask;
        for (int i = 13; i < 18; i++)
            mask |= (1 << i);
        int immediate = instruction & mask;
        for (int i = 18; i < 28; i++)
            mask |= (1 << i);
        int address = instruction & mask;

        int msb = immediate & (1 << 17);
        if ((msb >> 17) == 1) {
            mask = 0;
            for (int i = 18; i < 32; i++)
                mask |= (1 << i);
            immediate |= mask;
        }

        HashMap<String, Integer> ID_EX = PipelineRegisters.getPipelineRegisterInstance().getID_EX();

        ID_EX.put("opcodeLeft", opcode);
        ID_EX.put("r1ContentLeft", 0);
        ID_EX.put("r2ContentLeft", 0);
        ID_EX.put("r3ContentLeft", 0);
        ID_EX.put("immediateLeft", 0);
        ID_EX.put("shamtLeft", 0);
        ID_EX.put("r1Left", 0);
        ID_EX.put("addressLeft", 0);
        ID_EX.put("regWriteLeft", 0);
        ID_EX.put("pcLeft", PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("pcRight", 0));
        ID_EX.put("availableLeft", PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("availableRight", 0));

        int r1Content;
        int r2Content;
        int r3Content;
        switch (opcode) {
            case 0:
            case 1:
            case 8:
            case 9: {
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                r3Content = RegisterFile.getRegisterFileInstance().readFromRegister(r3);
                ID_EX.put("r2ContentLeft", r2Content);
                ID_EX.put("r3ContentLeft", r3Content);
                ID_EX.put("shamtLeft", shamt);
                ID_EX.put("r1Left", r1);
                ID_EX.put("regWriteLeft", 1);
                break;
            }
            case 7: {
                //TODO: check if this is correct
                //to be reviewed / moved to execute
//                int oldPC = RegisterFile.getRegisterFileInstance().getPC();
//                //concatenate bits 31:28 of oldPC with bits 27:0 of address
//                int newPC = (oldPC & 0b11110000000000000000000000000000) | address;
//                RegisterFile.getRegisterFileInstance().setPC(newPC);
                ID_EX.put("addressLeft", address);
                break;
            }
            case 4:
            case 11: {
                r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                ID_EX.put("r1ContentLeft", r1Content);
                ID_EX.put("r2ContentLeft", r2Content);
                ID_EX.put("immediateLeft", immediate);
                ID_EX.put("r1Left", r1);
            }
            default: {
                r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                ID_EX.put("r1ContentLeft", r1Content);
                ID_EX.put("r2ContentLeft", r2Content);
                ID_EX.put("immediateLeft", immediate);
                ID_EX.put("r1Left", r1);
                ID_EX.put("regWriteLeft", 1);

            }

        }

    }

    public void print() {
        if (PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("availableRight", 0) == 0) {
            System.out.println("No instruction is executed in ID stage");
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in ID stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("--------------------------------------------------");
        System.out.print("Outputs: ");
        printOutput();
        System.out.println("--------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs: ");
        System.out.println("Instruction: " + Integer.toBinaryString(PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0)));
    }

    public void printOutput() {

        int instruction = PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0);

        int opcode = instruction >>> 28;
        int mask = 0;
        for (int i = 0; i < 5; i++)
            mask |= (1 << i);
        int r1 = (instruction >>> 23) & mask;
        int r2 = (instruction >>> 18) & mask;
        int r3 = (instruction >>> 13) & mask;
        for (int i = 5; i < 13; i++)
            mask |= (1 << i);
        int shamt = instruction & mask;
        for (int i = 13; i < 18; i++)
            mask |= (1 << i);
        int immediate = instruction & mask;
        for (int i = 18; i < 28; i++)
            mask |= (1 << i);
        int address = instruction & mask;

        int msb = immediate & (1 << 17);
        if ((msb >> 17) == 1) {
            mask = 0;
            for (int i = 18; i < 32; i++)
                mask |= (1 << i);
            immediate |= mask;
        }
        int r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
        int r3Content = RegisterFile.getRegisterFileInstance().readFromRegister(r3);

        System.out.println("Opcode: " + Integer.toBinaryString(opcode) + "\nR1: " + Integer.toBinaryString(r1) + "\nR2 Content: " + r2Content + "\nR3 Content: " + r3Content + "\nShamt: " + Integer.toBinaryString(shamt) + "\nImmediate: " + Integer.toBinaryString(immediate) + "\nAddress: " + Integer.toBinaryString(address));
    }

}
