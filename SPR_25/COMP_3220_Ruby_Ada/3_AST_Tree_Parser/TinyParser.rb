#
#  Parser Class
#
load "TinyLexer.rb"
load "TinyToken.rb"
load "AST.rb"

class Parser < Lexer

    def initialize(filename)
        super(filename)
        consume()
    end

    def consume()
        @lookahead = nextToken()
        while(@lookahead.type == Token::WS)
            @lookahead = nextToken()
        end
    end

    def match(dtype)
        if (@lookahead.type != dtype)
            puts "Expected #{dtype} found #{@lookahead.text}"
			@errors_found+=1
        end
        consume()
    end

    def program()
    	@errors_found = 0
		
		p = AST.new(Token.new("program","program"))
		
	    while( @lookahead.type != Token::EOF)
            p.addChild(statement())
        end
        
        puts "There were #{@errors_found} parse errors found."
      
		return p
    end

    def statement()
		stmt = AST.new(Token.new("statement","statement"))
        if (@lookahead.type == Token::PRINT)
			stmt = AST.new(@lookahead)
            match(Token::PRINT)
            stmt.addChild(exp())
        else
            stmt = assign()
        end
		return stmt
    end

    def exp()
        t = term()
        e = etail()
        if (e)
            e.addChild(t)
            return e
        else
            return t
        end
    end

    def term()
        f = factor()
        t = ttail()
        if (t)
            t.addChild(f)
            return t
        else
            return f
        end
    end

    def factor()
        if (@lookahead.type == Token::LPAREN)
            match(Token::LPAREN)    
            fct_node = exp()
            if (@lookahead.type == Token::RPAREN)
                match(Token::RPAREN)
            else
				match(Token::RPAREN)
            end
            return fct_node
        elsif (@lookahead.type == Token::INT)
            int_node = AST.new(@lookahead)
            match(Token::INT)
            return int_node
        elsif (@lookahead.type == Token::ID)
            id_node = AST.new(@lookahead)
            match(Token::ID)
            return id_node
        else
            puts "Expected ( or INT or ID found #{@lookahead.text}"
            @errors_found+=1
            consume()
        end
		return fct
    end

    def ttail()
        if (@lookahead.type == Token::MULTOP)
            multop = AST.new(@lookahead)
            match(Token::MULTOP)
            multop.addChild(factor())
            multop.addChild(ttail())
            return multop
        elsif (@lookahead.type == Token::DIVOP)
            divop = AST.new(@lookahead)
            match(Token::DIVOP)
            divop.addChild(factor())
            divop.addChild(ttail())
            return divop
		else
			return nil
        end
    end

    def etail()
        if (@lookahead.type == Token::ADDOP)
            addop = AST.new(@lookahead)
            match(Token::ADDOP)
            addop.addChild(term())
            addop.addChild(etail())
            return addop
        elsif (@lookahead.type == Token::SUBOP)
            subop = AST.new(@lookahead)
            match(Token::SUBOP)
            subop.addChild(term())
            subop.addChild(etail())
            return subop
		else
			return nil
        end
    end

    def assign()
        assgn = AST.new(Token.new("assignment","assignment"))
		if (@lookahead.type == Token::ID)
			idtok = AST.new(@lookahead)
			match(Token::ID)
			if (@lookahead.type == Token::ASSGN)
				assgn = AST.new(@lookahead)
				assgn.addChild(idtok)
            	match(Token::ASSGN)
				assgn.addChild(exp())
        	else
				match(Token::ASSGN)
			end
		else
			match(Token::ID)
        end
		return assgn
	end
end
