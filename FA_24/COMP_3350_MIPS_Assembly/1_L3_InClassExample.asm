.data
A: .word 7, 42, 0, 27, 16, 8, 4, 15, 31, 45
n: .word 3

.text
.globl main

# Swap 2 adjacent elements A[N], A[N+1] in the array
main:
	la $a0, A # Load address of array A to $a0
	lw $a1, n # Load word
	#addi $a1, $zero, 3
	
	# shift left logical
	sll $t1, $a1, 2 # Calculate offset for array index
	add $t1, $a0, $t1 # Calculate address of array index
	lw $t0, 0($t1) # Load value at array index N to $t0
	lw $t2, 4($t1) # Load value at array index N+1 to $t2
	sw $t2, 0($t1) # Store $t2 at array index
	sw $t0, 4($t1) # Store $t0 at array index + 4
