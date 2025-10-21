#
#  Class Lexer - Reads a TINY program and emits tokens
#
class Lexer
  # Constructor - Is passed a file to scan and outputs a token
  #               each time nextToken() is invoked.
  #   @c        - A one character lookahead 
  def initialize(filename)
    # Need to modify this code so that the program
    # doesn't abend if it can't open the file but rather
    # displays an informative message
    begin
      @f = File.open(filename, 'r:utf-8')
    rescue => e
      puts "Error: Unable to open file #{filename}"
      return
    end

    # Go ahead and read in the first character in the source
    # code file (if there is one) so that you can begin
    # lexing the source code file 
    if (!@f.eof?)
      @c = @f.getc()
    else
      @c = "eof"
      @f.close()
    end
  end
  
  # Method nextCh() returns the next character in the file
  def nextCh()
    if (!@f.eof?)
      @c = @f.getc()
    else
      @c = "eof"
    end
    
    return @c
  end

  # Method nextToken() reads characters in the file and returns
  # the next token
  def nextToken()
    if @c == "eof"
      return Token.new(Token::EOF, "eof")
    
    elsif whitespace?(@c)
      str = ""
      while whitespace?(@c)
        str += @c
        nextCh()
      end
      tok = Token.new(Token::WS, str)
      return tok

    elsif parenthesis?(@c)
      if @c == "("
        tok = Token.new(Token::LPAREN, @c)
      elsif @c == ")"
        tok = Token.new(Token::RPAREN, @c)
      else
        tok = Token.new(Token::UNKWN, @c)
      end
      nextCh()
      return tok

    elsif letter?(@c)
      str = ""
      while letter?(@c)
        str += @c
        nextCh()
      end
      if str == "print"
        tok = Token.new(Token::PRINT, str)
      elsif str.length > 1
        tok = Token.new(Token::VAR, str)
      else
        tok = Token.new(Token::ID, str)
      end
      return tok

    elsif operator?(@c)
      if @c == "+"
        tok = Token.new(Token::ADDOP, @c)
      elsif @c == "/"
        tok = Token.new(Token::DIVOP, @c)
      else
        tok = Token.new(Token::UNKWN, @c)
      end
      nextCh()
      return tok

    elsif assignment?(@c)
      if @c == "="
        tok = Token.new(Token::ASSIGN, @c)
      else
        tok = Token.new(Token::UNKWN, @c)
      end
      nextCh()
      return tok

    elsif numeric?(@c)
      str = ""
      while numeric?(@c)
        str += @c
        nextCh()
      end
      tok = Token.new(Token::NUM, str)
      return tok

    else
      tok = Token.new(Token::UNKWN, @c)
      nextCh()
      return tok
    end
  end

  #
  # Helper methods for Scanner
  #
  def letter?(lookAhead)
    lookAhead =~ /^[a-zA-Z]$/
  end
  
  def numeric?(lookAhead)
    lookAhead =~ /^\d+$/
  end
  
  def whitespace?(lookAhead)
    lookAhead =~ /^\s+$/
  end

  def operator?(lookAhead)
    lookAhead =~ /^[+\/]$/
  end

  def assignment?(lookAhead)
    lookAhead =~ /^=$/
  end

  def parenthesis?(lookAhead)
    lookAhead =~ /^[()]$/
  end
end