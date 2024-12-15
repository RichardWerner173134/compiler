/* --------------------------Usercode Section------------------------ */
package com.werner.compiler.generated;

import java_cup.runtime.*;

%%

/* -----------------Options and Declarations Section----------------- */

/*
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java.
*/
%class Lexer

/*
   Make generated class public.
 */
%public

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column

/*
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup

/*
  Declarations
*/
%{
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}


/*
  Macro Declarations

  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.
*/

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n

/* White space is a line terminator, space, tab, or line feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]

/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
INT_LIT = 0 | [1-9][0-9]*

/* A identifier is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
IDENT = [A-Za-z_][A-Za-z_0-9]*

/*matches string-literal on a single line*/
STRING_LIT = \"([^\\\"]|\\.)*\"

%%
/* ------------------------Lexical Rules Section---------------------- */

/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

   /* YYINITIAL is the state at which the lexer begins scanning.  So
   these regular expressions will only be matched if the scanner is in
   the start state YYINITIAL. */

<YYINITIAL> {

    /* Return the token SEMI declared in the class sym that was found. */
    ";"                { return symbol(sym.SEMICOLON); }
    ":"                { return symbol(sym.DOUBLECOLON); }
    ","                { return symbol(sym.COMMA); }

    /* Print the token found that was declared in the class sym and then
       return it. */
    "+"                { return symbol(sym.PLUS); }
    "-"                { return symbol(sym.MINUS); }
    "*"                { return symbol(sym.STAR); }
    "/"                { return symbol(sym.SLASH); }
    "("                { return symbol(sym.LPAREN); }
    ")"                { return symbol(sym.RPAREN); }
    "=="               { return symbol(sym.EQUALS); }
    "!="               { return symbol(sym.NEQUALS); }
    "="                { return symbol(sym.ASGN); }
    "{"                { return symbol(sym.LCURLYBRACE);}
    "}"                { return symbol(sym.RCURLYBRACE);}
    "["                { return symbol(sym.LSQUAREBRACKET);}
    "]"                { return symbol(sym.RSQUAREBRACKET);}
    "<="               { return symbol(sym.LESSOREQUAL);}
    ">="               { return symbol(sym.MOREOREQUAL);}
    "<"               { return symbol(sym.LESS);}
    ">"               { return symbol(sym.MORE);}

    "true"             { return symbol(sym.TRUE);}
    "false"            { return symbol(sym.FALSE);}
    "if"               { return symbol(sym.IF);}
    "else"             { return symbol(sym.ELSE);}
    "while"            { return symbol(sym.WHILE);}

    "string"           {return symbol(sym.STRING);}
    "int"              {return symbol(sym.INTEGER);}
    "bool"             {return symbol(sym.BOOLEAN);}

    "var"              {return symbol(sym.VAR);}

    "of"               {return symbol(sym.OF);}
    "array"            {return symbol(sym.ARRAY);}

    "record"           {return symbol(sym.RECORD);}

    "type"             {return symbol(sym.TYPE);}

    "function"         {return symbol(sym.FUNCTION);}
    "return"           {return symbol(sym.RETURN);}

    "new"              {return symbol(sym._NEW);}

    {INT_LIT}          { return symbol(sym.NUMBER, new Integer(yytext())); }

    {STRING_LIT}       {
          String literal = yytext();
          return symbol(sym.STRING_LITERAL, literal.substring(1, literal.length() - 1));
      }

    /* If an identifier is found print it out, return the token ID
       that represents an identifier and the default value one that is
       given to all identifiers. */
    {IDENT}            { return symbol(sym.IDENT, yytext());}

    /* Don't do anything if whitespace is found */
    {WhiteSpace}       { /* just skip what was found, do nothing */ }

}


/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }