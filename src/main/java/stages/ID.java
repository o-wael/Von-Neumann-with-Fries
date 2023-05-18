package stages;

import memory.MainMemory;
import memory.PipelineRegisters;
import memory.RegisterFile;

public class ID {

    private static final ID idInstance = new ID();
    private int opcode;
    private int r1;
    private int r2;
    private int r3;
    private int r1Content;
    private int r2Content;
    private int r3Content;
    private int shamt;
    private int immediate;
    private int address;
    private int instructionNumber;


    public void decodeInstruction() {
        int instruction = PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0);
        opcode = instruction >>> 28;
        r1 = (instruction >>> 23) & 0b11111;
        r2 = (instruction >>> 18) & 0b11111;
        r3 = (instruction >>> 13) & 0b11111;
        shamt = instruction & 0b1111111111111;
        immediate = instruction & 0b111111111111111111;
        address = instruction & 0b1111111111111111111111111111;
        PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("opcodeLeft", opcode);
    }

    public void readValues() {
        switch (opcode) {
            case 0:
            case 1:
            case 8:
            case 9: {
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                r3Content = RegisterFile.getRegisterFileInstance().readFromRegister(r3);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r2ContentLeft", r2Content);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r3ContentLeft", r3Content);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("shamtLeft", shamt);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r1Left", r1);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("regWriteLeft", 1);
                break;
            }
            case 7: {
                //TODO: check if this is correct
                //to be reviewed / moved to execute
//                int oldPC = RegisterFile.getRegisterFileInstance().getPC();
//                //concatenate bits 31:28 of oldPC with bits 27:0 of address
//                int newPC = (oldPC & 0b11110000000000000000000000000000) | address;
//                RegisterFile.getRegisterFileInstance().setPC(newPC);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("regWriteLeft", 0);
                break;
            }
            case 4:
            case 11: {
                r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r1ContentLeft", r1Content);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r2ContentLeft", r2Content);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("immediateLeft", immediate);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r1Left", r1);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("regWriteLeft", 0);
            }
            default: {
                r1Content = RegisterFile.getRegisterFileInstance().readFromRegister(r1);
                r2Content = RegisterFile.getRegisterFileInstance().readFromRegister(r2);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r1ContentLeft", r1Content);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r2ContentLeft", r2Content);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("immediateLeft", immediate);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("r1Left", r1);
                PipelineRegisters.getPipelineRegisterInstance().getID_EX().put("regWriteLeft", 1);

            }

        }
    }

    public static ID getIDInstance() {
        return idInstance;
    }

    public void print() {
        instructionNumber = PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionNumberRight", 0);
        System.out.println("Instruction Being Executed in ID stage: " + instructionNumber);
        System.out.println("Inputs:\nInstruction: " + Integer.toBinaryString(PipelineRegisters.getPipelineRegisterInstance().getIF_ID().getOrDefault("instructionRight", 0)));
        System.out.println("Outputs:\nOpcode: " + Integer.toBinaryString(opcode) + "\nR1: " + Integer.toBinaryString(r1) + "\nR2 Content: " + r2Content + "\nR3 Content: " + r3Content + "\nShamt: " + Integer.toBinaryString(shamt) + "\nImmediate: " + Integer.toBinaryString(immediate) + "\nAddress: " + Integer.toBinaryString(address));
    }

}
