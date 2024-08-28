package com.werner.compiler;

import com.werner.compiler.ast.Program;
import com.werner.compiler.generated.Lexer;
import com.werner.compiler.generated.Parser;
import com.werner.compiler.symboltable.SymbolTable;
import com.werner.compiler.symboltable.visitor.NameAnalysisVisitor;
import com.werner.compiler.symboltable.visitor.Visitor;
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

            Program program = (Program) parse.value;
            String treeVisualization = program.print(0);
            System.out.println(treeVisualization);

            Visitor visitor = new NameAnalysisVisitor();
            visitor.visit(program);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
