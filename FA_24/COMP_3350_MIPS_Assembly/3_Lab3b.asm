.data	
	# Intialized variables and input space
	prompt: .asciiz "Enter an integer greater than 0: "
	response1: .asciiz "\nElement "
	response2: .asciiz " of the Fibonacci sequence is: "
	inputNum: .word 
	fibs: .word 0:30
	size: .space 30

.text
	main:
		# Print intial prompt
		li $v0, 4
		la $a0, prompt
		syscall
		
		# Read user input 
		li $v0, 5
		la $a0, inputNum
		syscall
		
	fib: 
		bge $a0, 1, fibonacciRecursive
		move $v0, $a0
		jr $ra
		
	fibonacciRecusive:
		sub $sp, $sp, -12
		sw $ra, 0($sp)
		sw $s0, 4($sp)
		move $s0, $a0
		
		add $a0, $s0, -1
		jal fib
		sw $v0, 8($sp)
		
		add $a0, $s0, -2
		jal fib
		
		lw $v0, 8($sp)
		add $v0, $a0, $v0
		
		lw $s0, 4($sp)
		lw $ra, 0($sp)
		add $sp, $sp, 12
		
		jr $ra
		
		
		
	# Print intial prompt
	li $v0, 4
	la $a0, response1
	syscall
		
	li $v0, 1
	la $a0, ($s0)
	syscall
	
	li $v0, 4
	la $a0, response2
	syscall
	
	# Exit program
	li $v0, 10
	syscall
