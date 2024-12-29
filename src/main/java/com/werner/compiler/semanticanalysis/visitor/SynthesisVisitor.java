package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.statements.AssignStatement;
import com.werner.compiler.ast.statements.Statement;
import com.werner.compiler.codesynthesis.CodeEmitter;
import com.werner.compiler.codesynthesis.Operator;
import com.werner.compiler.codesynthesis.ThreeAddressCode;
import com.werner.compiler.codesynthesis.ThreeAddressCodeTransformer;
import com.werner.compiler.semanticanalysis.Symbol;
import com.werner.compiler.semanticanalysis.SymbolTable;
import com.werner.compiler.semanticanalysis.info.Info;
import com.werner.compiler.semanticanalysis.info.VariableInfo;
import com.werner.compiler.semanticanalysis.type.Type;

import java.util.List;

public class SynthesisVisitor extends EmptyVisitor {
    public final SymbolTable symbolTable;
    private final CodeEmitter codeEmitter;
    private final ThreeAddressCodeTransformer threeAddressCodeTransformer;

    public SynthesisVisitor(
            SymbolTable symbolTable
    ) {
        this.symbolTable = symbolTable;
        this.codeEmitter = new CodeEmitter();
        this.threeAddressCodeTransformer = new ThreeAddressCodeTransformer();
    }

    @Override
    public void visit(Program program) {
        String globalEntry = ".global\n";
        codeEmitter.emit(globalEntry);
        for (Statement statement : program.statementList) {
            statement.accept(this);
        }

        codeEmitter.print();
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        for (Statement argumentDeclarations : functionDeclaration.statementList) {
            argumentDeclarations.accept(this);
        }
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
        for (Statement s : procedureDeclaration.statementList) {
            if (s instanceof AssignStatement) {
                Expression expression = ((AssignStatement) s).value;
                List<ThreeAddressCode> tacs = threeAddressCodeTransformer.transform(expression);
                tacs.forEach(tac -> codeEmitter.emit(tac.toString()));
                ThreeAddressCode last = tacs.getLast();

                Symbol symbol = new Symbol(((AssignStatement) s).target.name);
                Info info = symbolTable.lookup(symbol);
                ((VariableInfo)info).address = last.getTarget();

                ThreeAddressCode assignmentTac = new ThreeAddressCode(((AssignStatement) s).target.name, Operator.ADD, ((VariableInfo) info).address, "0");
                codeEmitter.emit(assignmentTac.toString());
            }
        }
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        Symbol symbol = new Symbol(variableDeclaration.identifier.name);
        Info info = symbolTable.lookup(symbol);

        ((VariableInfo)info).ALLOCATION_SIZE = 1;
    }
}
