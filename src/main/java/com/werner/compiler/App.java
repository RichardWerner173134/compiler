package com.werner.compiler;

import com.werner.compiler.ast.Program;
import com.werner.compiler.codesynthesis.CodeEmitter;
import com.werner.compiler.codesynthesis.NameProvider;
import com.werner.compiler.codesynthesis.ThreeAddressCodeTransformer;
import com.werner.compiler.codesynthesis.javasynthesis.RecordClassContainer;
import com.werner.compiler.generated.Lexer;
import com.werner.compiler.generated.Parser;
import com.werner.compiler.semanticanalysis.visitor.JavaSynthesisVisitor;
import com.werner.compiler.semanticanalysis.visitor.NameAnalysisVisitor;
import com.werner.compiler.semanticanalysis.visitor.SynthesisVisitor;
import java_cup.runtime.Symbol;

import javax.naming.Name;
import java.io.*;

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

            // NameProvider nameProvider = new NameProvider();
            CodeEmitter codeEmitter = new CodeEmitter();
            RecordClassContainer recordClassContainer = new RecordClassContainer();
            // ThreeAddressCodeTransformer threeAddressCodeTransformer = new ThreeAddressCodeTransformer(new NameProvider());

            // SynthesisVisitor synthesisVisitor = new SynthesisVisitor(outerNameAnalysisVisitor.symbolTable, codeEmitter, threeAddressCodeTransformer, nameProvider);
            // synthesisVisitor.visit(program);

            JavaSynthesisVisitor javaSynthesisVisitor = new JavaSynthesisVisitor(outerNameAnalysisVisitor.symbolTable, codeEmitter, recordClassContainer);
            javaSynthesisVisitor.visit(program);

            System.out.println("\nPHASE 3 finished: Code Synthesis");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
