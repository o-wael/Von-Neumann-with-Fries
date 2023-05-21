package parser;

import memory.MainMemory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public static void readAssemblyFile() throws FileNotFoundException {
        File file = new File("src/main/resources/assembly.txt");
        Scanner fileScanner = new Scanner(file);
        int index = 0;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            int instruction = parse(line);
            MainMemory.getMainMemoryInstance().memWrite(index, instruction);
            MainMemory.getMainMemoryInstance().assemblyWrite(index, line);
            index++;
        }
        MainMemory.getMainMemoryInstance().setCurNumOfInstructions(index);
        fileScanner.close();
    }

    public static int parse(String instruction) {
        String[] instructionParts = instruction.split(" ");
        String opcode = instructionParts[0];
        StringBuilder sb = new StringBuilder();
        switch (opcode) {
            case "ADD" -> sb.append("0000");
            case "SUB" -> sb.append("0001");
            case "MULI" -> sb.append("0010");
            case "ADDI" -> sb.append("0011");
            case "BNE" -> sb.append("0100");
            case "ANDI" -> sb.append("0101");
            case "ORI" -> sb.append("0110");
            case "J" -> sb.append("0111");
            case "SLL" -> sb.append("1000");
            case "SRL" -> sb.append("1001");
            case "LW" -> sb.append("1010");
            case "SW" -> sb.append("1011");
        }
        switch (opcode) {
            case "ADD", "SUB" -> {
                sb.append(parseRegister(instructionParts[1]));
                sb.append(parseRegister(instructionParts[2]));
                sb.append(parseRegister(instructionParts[3]));
                sb.append("0000000000000");
            }
            case "SLL", "SRL" -> {
                sb.append(parseRegister(instructionParts[1]));
                sb.append(parseRegister(instructionParts[2]));
                sb.append("00000");
                sb.append(parseShamt(instructionParts[3]));
            }
            case "MULI", "ADDI", "BNE", "ANDI", "ORI", "LW", "SW" -> {
                sb.append(parseRegister(instructionParts[1]));
                sb.append(parseRegister(instructionParts[2]));
                sb.append(parseImmediate(instructionParts[3]));
            }
            case "J" -> sb.append(parseAddress(instructionParts[1]));
        }
        return (int) (Long.parseLong(sb.toString(), 2));
    }

    private static String parseImmediate(String immediate) {
        int imm = Integer.parseInt(immediate);
        String binaryImm = Integer.toBinaryString(imm);
        StringBuilder sb = new StringBuilder();
        if (imm < 0) {
            sb.append(binaryImm.substring(14));
        } else {
            sb.append("0".repeat(Math.max(0, 18 - binaryImm.length())));
            sb.append(binaryImm);
        }

        return sb.toString();
    }

    private static String parseShamt(String shamt) {
        int imm = Integer.parseInt(shamt);
        String binaryImm = Integer.toBinaryString(imm);
        StringBuilder sb = new StringBuilder();
        if (imm < 0) {
            sb.append(binaryImm.substring(19));
        } else {
            sb.append("0".repeat(Math.max(0, 13 - binaryImm.length())));
            sb.append(binaryImm);
        }

        return sb.toString();
    }

    private static String parseAddress(String address) {
        int imm = Integer.parseInt(address);
        String binaryImm = Integer.toBinaryString(imm);
        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(Math.max(0, 28 - binaryImm.length())));
        sb.append(binaryImm);
        return sb.toString();
    }

    private static String parseRegister(String register) {

        int regNum = Integer.parseInt(register.substring(1));
        String reg = Integer.toBinaryString(regNum);
        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(Math.max(0, 5 - reg.length())));
        sb.append(reg);
        return sb.toString();

    }

    public static String parseMemory(int value) {
        String binaryValue = Integer.toBinaryString(value);
        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(32 - binaryValue.length()));
        sb.append(binaryValue);
        return sb.toString();
    }

}
