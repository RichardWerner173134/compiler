package com.werner.compiler;

import com.werner.compiler.ast.Program;
import com.werner.compiler.generated.Lexer;
import com.werner.compiler.generated.Parser;
import com.werner.compiler.semanticanalysis.visitor.NameAnalysisVisitor;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
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
            Symbol parse = parser.parse();
            System.out.println(parse.value.toString());

            Program program = (Program) parse.value;
            String treeVisualization = program.print(0);
            System.out.println(treeVisualization);

            Visitor outerNameAnalysisVisitor = new NameAnalysisVisitor();
            outerNameAnalysisVisitor.visit(program);

            int x = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
