.data
	# Intialized variables and input space
	prompt: .asciiz "Enter a string of size less than 40 characters: "
	
	userInput: .space 40

.text
	main:
		# Read user input as text, less than 40 char
		li $v0, 8
		la $a0, userInput
		li $a1, 40
		syscall
		
		#Displays prompt
		li $v0, 4
		la $a0, prompt
		syscall
		
		# Diplays message
		li $v0, 4
		la $a0, userInput
		syscall
		
		
		la $t1, userInput
		
	loop:
		lbu $t3, 0($t1)
		beq $t3, 0, Exit
		addi $t1, $t1, 1
		addi $t2, $t2, 1
		j loop
	Exit:
		# Exit program
		li $v0, 10
		syscall
