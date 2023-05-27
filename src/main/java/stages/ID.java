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

        HashMap<String, Integer> ID_EX = PipelineRegisters.getPipelineRegisterInstance().getID_EX();

        ID_EX.put("availableLeft", PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("availableRight", 0));
        if (PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("availableRight", 0) == 0) {
            return;
        }

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
        ID_EX.put("r2Left", r2);
        ID_EX.put("r3Left", r3);

        int r1Content;
        int r2Content;
        int r3Content;
        switch (opcode) {
            case 0, 1, 8, 9 -> {
                r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                r3Content = RegisterFile.getRegisterFileInstance().readFromRegister(r3);
                ID_EX.put("r1ContentLeft", r1Content);
                ID_EX.put("r2ContentLeft", r2Content);
                ID_EX.put("r3ContentLeft", r3Content);
                ID_EX.put("shamtLeft", shamt);
                ID_EX.put("r1Left", r1);
                ID_EX.put("regWriteLeft", 1);
            }
            case 7 -> {
                ID_EX.put("addressLeft", address);
            }
            case 4, 11 -> {
                r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                ID_EX.put("r1ContentLeft", r1Content);
                ID_EX.put("r2ContentLeft", r2Content);
                ID_EX.put("immediateLeft", immediate);
                ID_EX.put("r1Left", r1);
            }
            case 2, 3, 5, 6, 10 -> {
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
            System.out.println("------------------------------------------------------------");
            return;
        }
        int pc = PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("pcRight", 0);
        System.out.println("Instruction Being Executed in ID stage: " + MainMemory.getMainMemoryInstance().assemblyRead(pc));
        printInput();
        System.out.println("------------------------------------------------------------");
        System.out.print("Outputs: ");
        printOutput();
        System.out.println("------------------------------------------------------------");
    }

    public void printInput() {
        System.out.println("Inputs: ");
        StringBuilder sb = new StringBuilder();
        sb.append("Instruction: ");
        sb.append("0".repeat(Math.max(0, 32 - Integer.toBinaryString(PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0)).length())));
        sb.append(Integer.toBinaryString(PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0)));
        System.out.println(sb);
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

        int r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
        int r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
        int r3Content = RegisterFile.getRegisterFileInstance().readFromRegister(r3);

        StringBuilder sb = new StringBuilder();
        sb.append("Opcode: ");
        sb.append("0".repeat(Math.max(0, 4 - Integer.toBinaryString(opcode).length())));
        sb.append(Integer.toBinaryString(opcode));

        sb.append("\nR1: ");
        sb.append("0".repeat(Math.max(0, 5 - Integer.toBinaryString(r1).length())));
        sb.append(Integer.toBinaryString(r1));

        sb.append("\nR2: ");
        sb.append("0".repeat(Math.max(0, 5 - Integer.toBinaryString(r2).length())));
        sb.append(Integer.toBinaryString(r2));

        sb.append("\nR3: ");
        sb.append("0".repeat(Math.max(0, 5 - Integer.toBinaryString(r3).length())));
        sb.append(Integer.toBinaryString(r3));

        sb.append("\nR1 Content: ");
        sb.append(r1Content);

        sb.append("\nR2 Content: ");
        sb.append(r2Content);

        sb.append("\nR3 Content: ");
        sb.append(r3Content);

        sb.append("\nShamt: ");
        sb.append("0".repeat(Math.max(0, 13 - Integer.toBinaryString(shamt).length())));
        sb.append(Integer.toBinaryString(shamt));

        sb.append("\nImmediate: ");
        sb.append("0".repeat(Math.max(0, 18 - Integer.toBinaryString(immediate).length())));
        sb.append(Integer.toBinaryString(immediate));

        sb.append("\nAddress: ");
        sb.append("0".repeat(Math.max(0, 28 - Integer.toBinaryString(address).length())));
        sb.append(Integer.toBinaryString(address));

        sb.append("\nRegister Write: ");
        sb.append(PipelineRegisters.getPipelineRegisterInstance().getID_EX().get("regWriteLeft"));

        System.out.println(sb);
    }

}
