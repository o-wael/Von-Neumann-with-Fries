# Von Neumann with Fries

## Description

This is a simple implementation of the Von Neumann architecture in Java.

### Memory Architecture

- Memory Size: 2048 * 32 bits
- Memory is divided into 2 main parts:
  - Data Memory: 1024 * 32 bits
  - Instruction Memory: 1024 * 32 bits

  | Memory | Address Range |
    | ------ | ------------- |
    | Data Memory | 1024 - 2047 |
    | Instruction Memory | 0 - 1023 |

- The main memory addresses are from (0 to 2047).
- Each memory block (row) contains 1 word which is 32 bits (4 bytes).
- The main memory is word addressable.
- Addresses from 0 to 1023 contain the program instructions.
- Addresses from 1024 to 2048 contain the data.
- The memory is modelled as an integer array of size 2048.

### Registers

- There are 33 registers in the CPU.
- Each register is 32 bits (4 bytes).
- The registers are divided into 3 main parts:
  - Zero Register: 1 register
  - General Purpose Registers: 31 registers
  - Program Counter: 1 register

    | Register | Address Range |
    | ------ | ------------- |
    | Zero Register | 0 |
    | General Purpose Registers | 1 - 31 |
    | Program Counter | PC |
- The registers are modelled as an integer array of size 32, and the pc is modelled as an integer.

### Instruction Set

<details>
<summary>Instructions implemented</summary>

| Instruction | Opcode | Format | Description |
| ----------- | ------ | ------ | ----------- |
| ADD | 0000 | R | Add two registers and store the result in a register |
| SUB | 0001 | R | Subtract two registers and store the result in a register |
| MULI | 0010 | I | Multiply a register by a constant and store the result in a register |
| ADDI | 0011 | I | Add a constant to a register and store the result in a register |
| BNE | 0100 | I | Branch to a label if two registers are not equal |
| ANDI | 0101 | I | Bitwise AND a register with a constant and store the result in a register |
| ORI | 0110 | I | Bitwise OR a register with a constant and store the result in a register |
| J | 0111 | J | Jump to an address |
| SLL | 1000 | R | Shift a register left logical by a constant and store the result in a register |
| SRL | 1001 | R | Shift a register right logical by a constant and store the result in a register |
| LW | 1010 | I | Load a word from memory into a register |
| SW | 1011 | I | Store a word from a register into memory |

</details>

### Instruction Format

<details>
<summary>R, I, and J formats</summary>

#### R Format

| Opcode | rs | rt | rd | shamt|
| ------ | -- | -- | -- | ---- |
| 4 bits | 5 bits | 5 bits | 5 bits | 13 bits |

#### I Format

| Opcode | rs | rt | immediate |
| ------ | -- | -- | --------- |
| 4 bits | 5 bits | 5 bits | 18 bits |

#### J Format

| Opcode | address |
| ------ | ------- |
| 4 bits | 28 bits |

</details>

### Datapath

Stages: 5

- Instruction Fetch (IF)
- Instruction Decode (ID)
- Execution (EX)
- Memory Access (MEM)
- Write Back (WB)

Pipeline Registers: 4, with a maximum of 4 instructions running in parallel, as IF and MEM cannot access the memory at the same time.

- IF/ID
- ID/EX
- EX/MEM
- MEM/WB

## Usage

### Running the program

- Clone the repository
  ```bash
    git clone https://github.com/o-wael/Von-Neumann-with-Fries.git
  ```

- Write an assembly program using the instruction set above, and save it in a file called `assembly.txt` and save that file in the directory: `src/main/resources`.

- Run the program in any java IDE by running the `ControlUnit.java` file.

### Example

#### Assembly Program
The following program calculates the sum of the first 10 numbers in memory and stores the result in register 1.

```assembly
ADDI R31, R0, 9
ADDI R2, R0, 0
LW R3, R2, 0
ADD R1, R1, R3
ADDI R2, R2, 1
BNE R2, R31, -4
```

## Tools

- [Java](https://www.java.com/en/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Maven](https://maven.apache.org/)

## Contributors

#### This project has been implemented by a team of five computer engineering students

- [Omar Wael](https://github.com/o-wael)
- [Ahmad Hoseiny](https://github.com/AhmadHoseiny)
- [Abdelrahman Salah](https://github.com/19AbdelrahmanSalah19)
- [Ali Hussein](https://github.com/AliAdam102002)
- [Logine Mohamed](https://github.com/logine20)
