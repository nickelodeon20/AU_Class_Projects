.data
	# Intialized prompts and empty array of 100 chars. 
	prompt: .asciiz "Your string (<100 chars.): " 
	response: .asciiz "Word Count: "
	
	myWords: .byte 0:100

.text
	main:
		# Print intial prompt
		li $v0, 4
		la $a0, prompt
		syscall
		
		# Receive string input from user
		li $v0, 8
		la $a0, myWords
		li $a1, 100
		syscall
		
		li $t5, 0		# Initialize word counter
		la $t0, ($a0)	# Set memory address to $t1
		
	# Iterates through string until null character
	# -- Counts all words (word = a-z to A-Z, ends when non a-z/A-Z encountered)
	charLoop:
		
		lbu $t2, 0($t0)       # Load character at myWords[$t1] into $t2
		addi $t0, $t0, 1	 # Byte char index (char counter)
		beq $t2, 32, wordCount  # If .asciiz == space then count word
		beq $t2, 0, Exit  # If .asciiz == space then count word
		beq $t0, 100, Exit    # Exits loop once 100 chars countedt
		j charLoop
		
	wordCount:
		addi $t5, $t5, 1      # word count += 1
		beq $t3, $t2, Exit
		j charLoop            # Counts characters again
	
	# Program Responses
	Exit:
		addi $t0, $t0, 1	 # Byte char index (char counter)
		lbu $t2, 0($t0)       # Load character at myWords[$t1] into $t2
		addi $t5, $t5, 1
		
		# Prints response prompt
		li $v0, 4
		la $a0, response
		syscall
		
		# Prints word count (integer)
		li $v0, 1
		la $a0, ($t5)
		syscall
		
		# Prints word count
	
	# Exits program
	li $v0, 10
	syscall

