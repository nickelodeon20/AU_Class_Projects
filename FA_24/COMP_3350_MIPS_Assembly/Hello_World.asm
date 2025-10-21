.data
msg: .asciiz "hello world" 


.text
.globl main
main:
li $v0, 4 # Call to print String
la $a0, msg # Load variable from .data
syscall
li $v0, 10 # Call to exit program
syscall 