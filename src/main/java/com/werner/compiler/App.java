package com.werner.compiler;

import com.werner.compiler.generated.Lexer;
import com.werner.compiler.generated.Parser;
import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        Lexer lexer = new Lexer(new FileReader(args[0]));
        Parser parser = new Parser(lexer);

        try {
            Symbol parse = parser.parse();
            System.out.println(parse.value.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
