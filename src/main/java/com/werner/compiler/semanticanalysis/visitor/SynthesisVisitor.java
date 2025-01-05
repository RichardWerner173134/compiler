package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.expressions.BinaryExpression;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.expressions.VariableExpression;
import com.werner.compiler.ast.statements.*;
import com.werner.compiler.codesynthesis.*;
import com.werner.compiler.semanticanalysis.Constants;
import com.werner.compiler.semanticanalysis.Symbol;
import com.werner.compiler.semanticanalysis.SymbolTable;

import java.util.Collections;
import java.util.List;

public class SynthesisVisitor extends EmptyVisitor {
    public final SymbolTable symbolTable;
    private final CodeEmitter codeEmitter;
    private final ThreeAddressCodeTransformer threeAddressCodeTransformer;
    private final NameProvider nameProvider;

    public SynthesisVisitor(
            SymbolTable symbolTable,
            CodeEmitter codeEmitter,
            ThreeAddressCodeTransformer threeAddressCodeTransformer,
            NameProvider nameProvider
    ) {
        this.symbolTable = symbolTable;
        this.codeEmitter = codeEmitter;
        this.threeAddressCodeTransformer = threeAddressCodeTransformer;
        this.nameProvider = nameProvider;
    }

    @Override
    public void visit(Program program) {
        for (Statement statement : program.statementList) {

            // emit label for main procedure
            if (statement instanceof ProcedureDeclaration
                    && ((ProcedureDeclaration) statement).identifier.name.equals(Constants.MAIN_PROCEDURE_NAME)) {
                String globalEntry = ".global:";
                codeEmitter.emit(globalEntry);
            }

            Symbol symbol = new Symbol(String.valueOf(statement.hashCode()));
            SymbolTable innerSymbolTable = symbolTable.innerScopes.get().get(symbol);
            NameProvider newNameProvider = new NameProvider();
            SynthesisVisitor innerVisitor = new SynthesisVisitor(innerSymbolTable, codeEmitter, new ThreeAddressCodeTransformer(newNameProvider), newNameProvider);

            statement.accept(innerVisitor);
        }

        codeEmitter.emit("EXIT");
        codeEmitter.print();
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        String nextLabel = nameProvider.getNextLabel();
        codeEmitter.emit("FUNCTION" + nextLabel + ":");

        for (Statement statement : functionDeclaration.statementList) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
        String nextLabel = nameProvider.getNextLabel();
        codeEmitter.emit("PROCEDURE " + nextLabel + ":");

        for (Statement statement : procedureDeclaration.statementList) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(AssignStatement assignStatement) {
        Expression expression = assignStatement.value;
        List<ThreeAddressCode> tacs = threeAddressCodeTransformer.transform(expression);
        tacs.forEach(tac -> codeEmitter.emit(tac.toString()));
        expression.tempVariable = tacs.getLast().getTarget();

        ThreeAddressCode assignmentTac = new ThreeAddressCode(assignStatement.target.name, Operator.ADD, expression.tempVariable, "0");
        codeEmitter.emit(assignmentTac.toString());
    }

    @Override
    public void visit(IfStatement ifStatement) {
        String ifLabel = "IF" + nameProvider.getNextLabel();
        String elseLabel = "ELSE" + nameProvider.getNextLabel();
        String ifEndLabel = "IFEND" + nameProvider.getNextLabel();

        codeEmitter.emit(ifLabel + ":");

        // IF CONDITION CODE START

        ifStatement.condition.accept(this);

        codeEmitter.emit("JUMPifTRUE " + ifStatement.condition.tempVariable + " " + elseLabel);

        // IF CONDITION CODE END

        // IF STATEMENTS CODE START
        Symbol symbolIf = new Symbol(String.valueOf(ifStatement.ifStatements.hashCode()));
        SymbolTable innerSymbolTableIf = symbolTable.innerScopes.get().get(symbolIf);

        SynthesisVisitor innerVisitorIf = new SynthesisVisitor(innerSymbolTableIf, codeEmitter, threeAddressCodeTransformer, nameProvider);
        for (Statement statementIf : ifStatement.ifStatements) {
            statementIf.accept(innerVisitorIf);
        }

        // IF STATEMENTS CODE END

        codeEmitter.emit("JUMP " + ifEndLabel);

        // ELSE STATEMENTS CODE START
        codeEmitter.emit(elseLabel + ":");

        Symbol symbolElse = new Symbol(String.valueOf(ifStatement.elseStatements.hashCode()));
        SymbolTable innerSymbolTableElse = symbolTable.innerScopes.get().get(symbolElse);

        SynthesisVisitor innerVisitorElse = new SynthesisVisitor(innerSymbolTableElse, codeEmitter, threeAddressCodeTransformer, nameProvider);
        for (Statement statementElse : ifStatement.elseStatements.orElse(Collections.emptyList())) {
            statementElse.accept(innerVisitorElse);
        }
        // ELSE STATEMENTS CODE END

        codeEmitter.emit(ifEndLabel + ":");
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        String whileStartLabel = "WHILE" + nameProvider.getNextLabel();
        String whileEndLabel = "WHILEEND" + nameProvider.getNextLabel();

        codeEmitter.emit(whileStartLabel + ":");
        whileStatement.condition.accept(this);
        codeEmitter.emit("JUMPifFALSE " + whileStatement.condition.tempVariable + " " + whileEndLabel);

        Symbol symbolWhile = new Symbol(String.valueOf(whileStatement.statementList.hashCode()));
        SymbolTable innerSymbolTableWhile = symbolTable.innerScopes.get().get(symbolWhile);

        SynthesisVisitor innerVisitorWhile = new SynthesisVisitor(innerSymbolTableWhile, codeEmitter, threeAddressCodeTransformer, nameProvider);
        for (Statement statement : whileStatement.statementList) {
            statement.accept(innerVisitorWhile);
        }

        codeEmitter.emit("JUMP " + whileStartLabel);

        codeEmitter.emit(whileEndLabel + ":");
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        List<ThreeAddressCode> tacs = threeAddressCodeTransformer.transform(binaryExpression);
        binaryExpression.tempVariable = tacs.getLast().getTarget();

        tacs.forEach(tac -> codeEmitter.emit(tac.toString()));
    }

    @Override
    public void visit(TypedReturnStatement typedReturnStatement) {
        // CODE TYPED RETURN EXPRESSION START
        typedReturnStatement.expression.accept(this);
        // CODE TYPED RETURN EXPRESSION END

        codeEmitter.emit("RETURN " + typedReturnStatement.expression.tempVariable);
    }

    @Override
    public void visit(EmptyReturnStatement emptyReturnStatement) {
        codeEmitter.emit("RETURN");
    }

    @Override
    public void visit(VariableExpression variableExpression) {
        List<ThreeAddressCode> tacs = threeAddressCodeTransformer.transform(variableExpression);
        variableExpression.tempVariable = tacs.getLast().getTarget();

        tacs.forEach(tac -> codeEmitter.emit(tac.toString()));
    }
}
