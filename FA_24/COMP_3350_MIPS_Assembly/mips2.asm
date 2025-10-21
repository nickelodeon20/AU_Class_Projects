.text
	li $t0, 235
	li $t1, 267
	add $s0, $t0, $t1
.data
	procA:
		 # please replace X with your code
		addi $sp, $sp, -28
		sw $ra, 24($sp)
		sw $a1, 20($sp)
		sw $a0, 16($sp)
		sw $s3, 12($sp)
		sw $s2, 8($sp)
		sw $s1, 4($sp)
		sw $s0, 0($sp)
		$s0 = ...
		$s1 = ...
		$s2 = ...
		$s3 = ... 
		$a0 = ...
		$a1 = ...
		jal procB
		... = $s3 # (this is the value that was originally assigned in procA)
		... = $a0 # (this is the value that was originally passed to procA as an argument)
		$v0 = $a0 + $v0
		 # please replace Y with your code
		lw $s0, 0($sp)
		lw $s1, 4($sp)
		lw $s2, 8($sp)
		lw $s3, 12($sp)
		lw $a0, 16($sp)
		lw $a1, 20($sp)
		lw $ra, 24($sp)
		addi $sp, $sp, 28
		jr $ra
	
	procB:
		 # please replace P with your code
		addi $sp, $sp, -20
		sw $ra, 16($sp)
		sw $v0, 12($sp)
		sw $t0, 8($sp)
		sw $s3, 4($sp)
		sw $s1, 0($sp)
		... = $a0
		... = $a1
		$s1 = ...
		$s3 = ...
		$t0 = ...
		$v0 = ...
		 # please replace Q with your code
		lw $s1, 0($sp)
		lw $s3, 4($sp)
		lw $t0, 8($sp)
		lw $v0, 12($sp)
		lw $ra, 16($sp)
		addi $sp, $sp, 20
		jr $ra
