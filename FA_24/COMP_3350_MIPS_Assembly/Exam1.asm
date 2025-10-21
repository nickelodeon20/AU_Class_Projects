.data
	prompt1: .asciiz "Enter a string: "
	prompt2: .asciiz "\nEnter a word to search for: "
	found_msg: .asciiz "\nWord found!\nOccurrences: "
	not_found_msg: .asciiz "\nWord not found.\n"
	index_msg: .asciiz "\nFound at index: "
	input_str: .space 256 	# Reserve 256 bytes for the input string
	search_word: .space 50 	# Reserve 50 bytes for the search word
	comma_msg: .asciiz ", "
.text
	.globl main
main:
	# Prompts user for input string
	li $v0, 4 		# syscall for printing a string
	la $a0, prompt1	# Load address of prompt1 message
	syscall
	
	# Read input string
	li $v0, 8 		# syscall for reading a string
	la $a0, input_str 	# Store string in input_str
	li $a1, 256 	# Max length of input
	syscall
	
	# Prompt for search word
	li $v0, 4 		# syscall for printing a string
	la $a0, prompt2 	# Load address of prompt2 message
	syscall
	
	# Read search word
	li $v0, 8 		# syscall for reading search word
	la $a0, search_word 	# Store word in search_word
	li $a1, 50 	# Max length of word
	syscall
	
	# Initialize variables
	la $t0, input_str 	# Address of input string
	la $t1, search_word 	# Address of search word
	
	li $t3, 0 		# Occurrence counter
	li $t4, 0 		# Index pointer for input string
	li $t5, 0 		# Counter of search_word length
	li $t6, 0 		# Found flag (0 = not found, 1 = found)
	
	search_word_length:
		lbu $t4, 0($t1)       # Load character at myWords[$t1] into $t2
		beq $t4, 10, reset_registers # Start string search after word length is found
		addi $t1, $t1, 1	 # Byte char index (char counter)
		addi $t2, $t2, 1
		j search_word_length
	reset_registers:
		la $t1, search_word	# Reset $t1 after getting word length
		li $t4, 0
		j stringSearch
	# Begin search

	# 1) Loop through 256 bytes - checking for search_word[0] (first char of word)
	stringSearch:
		beq $t7, 10, countCheck	# Exit program when end of output reached
		lbu $t7, 0($t0)	# Load character at input_str[$t0] into $t7
		lbu $t8, 0($t1)	# Load character at search_word[$t1] into $t8
		beq $t7, $t8, storeIndex 	# Send to storeIndex then checkWord loop
		addi $t0, $t0, 1	# input_str[++]
		addi $s0, $s0, 1	# ANOTHER counter
		la $t1, search_word
		
		j stringSearch	# No match --> go back to loop
		
	# 2) If match --> Check
	storeIndex:
		# A) Store pointer in current iteration
		la $t4, ($t0)	# Store 1st index that matched 
		
		li $t5, 0		# Reset word length count to 0 before checkWord
		# B) Start iterating place on input_str 
		lbu $t7, 0($t4)	# Load input_str[$t4] into $t7
		lbu $t8, 0($t1)	# Load search_word[$t1] into $t8
		beq $t7, $t8, checkWord 	# Send to checkWord loop
		
		# 3) Else --> search_word[++], go back to Loop
		j stringSearch	# Jump back to original loop if index doesnt match
		
		# C) Loop through next bytes of search_word, possible_word
	checkWord:
		beq $t5, $t2, checkSymbols
		
		lbu $t7, 0($t4)	# Load input_str[$t4] into $t7
		lbu $t8, 0($t1)	# Load search_word[$t1] into $t8
		
		# D) If search_word[x] != possible_word[x] --> exit back to spot in original iteration
		bne $t7, $t8, stringSearch
		
		addi $t4, $t4, 1	# input_str[++]
		addi $t1, $t1, 1	# search_word[++]
		addi $t5, $t5, 1	# word_length[++]
		
		# E) If ALL bytes (input_str sequence = search_word) & size match
		ble $t5, $t2, checkWord
		j stringSearch
		
	checkSymbols:
		#addi $t4, $t4, -1	# input_str[--]
		lbu $t7, 0($t4)	# load byte
	
		beq $t7, 32, found
		beq $t7, 33, found	# Various punctuation symbols/space that are still grammatically correct
		beq $t7, 34, found 
		beq $t7, 39, found
		beq $t7, 41, found
		beq $t7, 44, found
		beq $t7, 46, found
		beq $t7, 10, found
		
		j stringSearch
		
	found:
		li $t6, 1		# set found flag to 1
		j reset_check
		
	reset_check:
		# --> Found flag = 1
		bne $t6, 1, stringSearch	# Return to input_str if found flag != 1
		la $t0, ($t4)	# Set $t0 to $t4 (end of completed word)
		li $t4, 0		# Reset input string index pointer to 0
		addi $t3, $t3, 1	# Occurrence ($t3) += 1
		la $t1, search_word	# Reset search_word pointer to 0
		li $t6, 0		# Reset found flag to 0 
		li $t5, 0		# Reset possible word length to 0
		
		# BONUS - Save in array start index of word (saved from beginning)
		addi $sp, $sp, -4	# Increment int. array address by 4
		addi $t9, $t9, 1	# Num. of times $sp is incremented
		sw $s0, 0($sp)	# Store index num. in $sp
		add $s0, $s0, $t5	# Add word length to current index to get index at end of word
		j stringSearch	# Jump back to Loop
	
	countCheck:
		bgt $t3, 0, printFound	# If Occurrences > 0, go to printFound
		j printNotFound		# Else printNotFound
		
	printFound: 
		# Print found msg
		li $v0, 4 		# syscall for printing a string
		la $a0, found_msg 	# Load address of found_msg message
		syscall
		
		# Print occurrences
		li $v0, 1 		# syscall for printing a string
		la $a0, ($t3) 	# Load address of occurrences count
		syscall
		
		# Index found
		li $v0, 4 		# syscall for printing a string
		la $a0, index_msg 	# Load address of index_msg message
		syscall
		
	occurrencesLoop:
		beq $t9, 0, Exit
		lw $t5, 0($sp)	# Load index from $sp
		addi $sp, $sp, 4	# Increment $sp
		addi $t9, $t9, -1	# Total times $sp used - 1 (counting down)
		
		li $v0, 1 		# syscall for printing a string
		la $a0, ($t5) 	# Load address of word indexes
		syscall
		
		bgt $t9, 0, comma 	# Printing format
		j occurrencesLoop
	comma:
		li $v0, 4 		# syscall for printing a string
		la $a0, comma_msg 	# Load address of index_msg message
		syscall	
		j occurrencesLoop
	
	printNotFound:
		# Index not found
		li $v0, 4 		# syscall for printing a string
		la $a0, not_found_msg 	# Load address of index_msg message
		syscall
	
	Exit:
		# End the program
		li $v0, 10 # Exit syscall
		syscall