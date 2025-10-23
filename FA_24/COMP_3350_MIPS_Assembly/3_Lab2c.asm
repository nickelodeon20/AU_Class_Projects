.data
	# Intialize integer 
	A: .word 10
	B: .word 15
	C: .word 7
	Z: .word 0

.text	
	main:
		# Load values of A and B
		lw $t0, A
		lw $t1, B
		lw $t2, C
		la $a0, Z
		
		# First if statement
		blt $t2, 5, C_LT_5
		
		#Second if statement
		bgt $t0, $t1, A_GT_B
		
		# Else statement for all other cases
		la $a0, 3
		j Switch
		
		
	# C<5 statement
	# -- set Z=1 if true
	C_LT_5:   
		la $a0, 1
		j Switch
		
	#A>B if statement
	# -- check for C==7
	A_GT_B:	
		addi $t2, $t2, 1 # C+1 = C (new value)
		beq $t2, 7, C_Equal_7
		# set $a0 to 1
		la $a0, 1
		j Switch
		
	#A>B && (C+1)=7
	C_Equal_7:
		# set $a0
		la $a0, 2
		j Switch
		
	# Switch cases 
	Switch: 
		# Checking for Z1 Case
		addi $t4, $zero, 1
		bne $a0, $t4, CaseZ_2
		la $a0, -1
		j End
			
		# Checking for Z2 Case
		CaseZ_2:
			addi $t4, $zero, 2
			bne $a0, $t4, default
			la $a0, -2
			j End
			
		# Checking for Z3 Case
		default:
			la $a0, 0
			j End

	# Store Z in memory, End program
	End: 	
		sw $a0, Z
		li $v0, 10
		syscall
