package com.werner.compiler;

import com.werner.compiler.ast.Program;
import com.werner.compiler.generated.Lexer;
import com.werner.compiler.generated.Parser;
import com.werner.compiler.semanticanalysis.visitor.NameAnalysisVisitor;
import com.werner.compiler.semanticanalysis.visitor.SynthesisVisitor;
import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.io.FileReader;

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

            System.out.println("\nPHASE 1 started: Lexer and Parser");
            Symbol parse = parser.parse();
            System.out.println(parse.value.toString());
            Program program = (Program) parse.value;
            String treeVisualization = program.print(0);
            System.out.println(treeVisualization);
            System.out.println("\nPHASE 1 finished: Lexer and Parser");

            System.out.println("\nPHASE 2 started: Semantic Analysis");
            NameAnalysisVisitor outerNameAnalysisVisitor = new NameAnalysisVisitor();
            outerNameAnalysisVisitor.visit(program);
            System.out.println("\nPHASE 2 finished: Semantic Analysis");

            System.out.println("\nPHASE 3 started: Code Synthesis");
            SynthesisVisitor synthesisVisitor = new SynthesisVisitor(outerNameAnalysisVisitor.symbolTable);
            synthesisVisitor.visit(program);
            System.out.println("\nPHASE 3 finished: Code Synthesis");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
