.data
	# Intialize integer 
	A: .word 15
	B: .word 10
	C: .word 7
	D: .word 2
	E: .word 18
	F: .word -3
	Z: .word 0

.text
	# Z = (A+B) + (C-D) + (E+F) - (A-C)
	
	main:
		la $a0, Z
		
		# 1st operation - sum
		lw $t0, A
		lw $t1, B
		add $t2, $t0, $t1
		
		# 2nd operation - difference
		lw $t0, C
		lw $t1, D
		sub $t3, $t0, $t1
		
		# 3rd operation - sum
		lw $t0, E
		lw $t1, F
		add $t4, $t0, $t1
		
		# 4th operation - difference
		lw $t0, A
		lw $t1, C
		sub $t5, $t0, $t1
		
		# 5th operation - sum of 1st and 2nd
		la $t0, ($t2) 
		la $t1, ($t3)
		add $t6, $t0, $t1
		
		# 6th operation - difference of 3rd and 4th
		la $t0, ($t4)
		la $t1, ($t5)
		sub $t7, $t0, $t1
		
		# 7th operation - sum of 5th and 6th
		la $t0, ($t6)
		la $t1, ($t7)
		add $a1, $t0, $t1
		
		# Send final value into integer Z's address
		sw $a1, ($a0)
		
		
	# Exit program
	
	li $v0, 10
	syscall
		
		
