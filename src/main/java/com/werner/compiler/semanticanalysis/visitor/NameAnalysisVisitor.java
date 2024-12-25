package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.FunctionCall;
import com.werner.compiler.ast.expressions.type.*;
import com.werner.compiler.ast.statements.*;
import com.werner.compiler.exceptions.CompilerError;
import com.werner.compiler.semanticanalysis.Symbol;
import com.werner.compiler.semanticanalysis.Kind;
import com.werner.compiler.semanticanalysis.info.*;
import com.werner.compiler.semanticanalysis.SymbolTable;
import com.werner.compiler.semanticanalysis.type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameAnalysisVisitor extends EmptyVisitor {
    public final SymbolTable symbolTable;

    public NameAnalysisVisitor() {
        this.symbolTable = new SymbolTable();

        this.symbolTable.enter(new Symbol(PrimitiveType.INT_TYPE.typeName), new TypeInfo(PrimitiveType.INT_TYPE, Kind.TYPE));
        this.symbolTable.enter(new Symbol(PrimitiveType.BOOLEAN_TYPE.typeName), new TypeInfo(PrimitiveType.BOOLEAN_TYPE, Kind.TYPE));
        this.symbolTable.enter(new Symbol(PrimitiveType.STRING_TYPE.typeName), new TypeInfo(PrimitiveType.STRING_TYPE, Kind.TYPE));
    }

    public NameAnalysisVisitor(SymbolTable outerSymbolTable) {
        // new symbolTable with ref to outer symbolTable
        this.symbolTable = new SymbolTable(outerSymbolTable);
    }

    @Override
    public void visit(Program program) {
        for (Statement statement : program.statementList) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        // allow functions only in global scope
        if (symbolTable.outerScope.isPresent()) {
            throw CompilerError.InvalidDeclarationLocation(functionDeclaration.location, functionDeclaration.identifier.name);
        }

        Type type = getType(functionDeclaration.returnType);

        List<VariableInfo> parameterInfos = functionDeclaration.parametersList
                .stream()
                .map(parameter -> {
                    Type parameterType = getType(parameter.typeExpression);
                    return new VariableInfo(false, parameterType);
                })
                .toList();

        // function name
        symbolTable.enter(
                new Symbol(functionDeclaration.identifier.name),
                new FunctionInfo(type, parameterInfos),
                CompilerError.RedeclarationOfType(functionDeclaration.location, functionDeclaration.identifier.name)
        );

        NameAnalysisVisitor innerVisitor = new NameAnalysisVisitor(symbolTable);

        // function parameters
        for (VariableDeclaration variableDeclaration : functionDeclaration.parametersList) {
            variableDeclaration.accept(innerVisitor);
        }

        // function statements
        for (Statement statement : functionDeclaration.statementList) {
            statement.accept(innerVisitor);
        }

        // function return type
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(innerVisitor.symbolTable);
        functionDeclaration.accept(typeAnalysisVisitor);
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
        // allow functions only in global scope
        if (symbolTable.outerScope.isPresent()) {
            throw CompilerError.InvalidDeclarationLocation(procedureDeclaration.location, procedureDeclaration.identifier.name);
        }

        List<VariableInfo> parameterInfos = procedureDeclaration.parametersList
                .stream()
                .map(parameter -> {
                    Type parameterType = getType(parameter.typeExpression);
                    return new VariableInfo(false, parameterType);
                })
                .toList();


        // procedure name
        symbolTable.enter(
                new Symbol(procedureDeclaration.identifier.name),
                new ProcedureInfo(parameterInfos),
                CompilerError.RedeclarationOfType(procedureDeclaration.location, procedureDeclaration.identifier.name)
        );

        NameAnalysisVisitor innerVisitor = new NameAnalysisVisitor(symbolTable);

        // procedure parameters
        for (VariableDeclaration variableDeclaration : procedureDeclaration.parametersList) {
            variableDeclaration.accept(innerVisitor);
        }

        // procedure statements
        for (Statement statement : procedureDeclaration.statementList) {
            statement.accept(innerVisitor);
        }

        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(innerVisitor.symbolTable);
        procedureDeclaration.accept(typeAnalysisVisitor);
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {
        // allow type declarations only in global scope
        if (symbolTable.outerScope.isPresent()) {
            throw CompilerError.InvalidDeclarationLocation(typeDeclaration.location, typeDeclaration.identifier.name);
        }

        Type type = getType(typeDeclaration.typeExpression);

        typeDeclaration.typeExpression.setSymbolType(type); // TODO ?

        symbolTable.enter(
                new Symbol(typeDeclaration.identifier.name),
                new TypeInfo(type, Kind.TYPE),
                CompilerError.RedeclarationOfType(typeDeclaration.location, typeDeclaration.identifier.name)
        );
    }

    @Override
    public void visit(VariableDeclarationStatement variableDeclarationStatement) {
        // allow type declarations only in local scope
        if (symbolTable.outerScope.isEmpty()) {
            throw CompilerError.InvalidDeclarationLocation(variableDeclarationStatement.location, variableDeclarationStatement.variableDeclaration.identifier.name);
        }

        variableDeclarationStatement.variableDeclaration.accept(this);
    }

    @Override
    public void visit(IfStatement ifStatement) {
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(symbolTable);
        ifStatement.accept(typeAnalysisVisitor);

        NameAnalysisVisitor innerVisitorIf = new NameAnalysisVisitor(symbolTable);

        for (Statement statement : ifStatement.ifStatements) {
            statement.accept(innerVisitorIf);
        }

        NameAnalysisVisitor innerVisitorElse = new NameAnalysisVisitor(symbolTable);
        for (Statement statement : ifStatement.elseStatements.orElse(Collections.emptyList())) {
            statement.accept(innerVisitorElse);
        }
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(symbolTable);
        whileStatement.accept(typeAnalysisVisitor);

        NameAnalysisVisitor innerVisitor = new NameAnalysisVisitor(symbolTable);
        for (Statement statement : whileStatement.statementList) {
            statement.accept(innerVisitor);
        }
    }

    @Override
    public void visit(AssignStatement assignStatement) {
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(symbolTable);
        assignStatement.accept(typeAnalysisVisitor);
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        Type type = getType(variableDeclaration.typeExpression);

        symbolTable.enter(
                new Symbol(variableDeclaration.identifier.name),
                new VariableInfo(false, type), // TODO implement refs
                CompilerError.RedeclarationOfType(variableDeclaration.location, variableDeclaration.identifier.name)
        );

        // typecheck
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(symbolTable);
        variableDeclaration.accept(typeAnalysisVisitor);
    }

    @Override
    public void visit(ProcedureCall procedureCall) {
        // typecheck
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(symbolTable);
        procedureCall.accept(typeAnalysisVisitor);
    }

    @Override
    public void visit(FunctionCall functionCall) {
        TypeAnalysisVisitor typeAnalysisVisitor = new TypeAnalysisVisitor(symbolTable);
        functionCall.accept(typeAnalysisVisitor);
    }

    private Type getType(AbstractTypeExpression expression) {
        if (expression instanceof PrimitiveTypeExpression) {
            Type result = new PrimitiveType(((PrimitiveTypeExpression) expression).primitiveType.name());
            return result;
        }

        if (expression instanceof ArrayTypeExpression) {
            Type baseType = getType(((ArrayTypeExpression) expression).typeExpression);
            Type result = new ArrayType(baseType);
            return result;
        }

        if (expression instanceof RecordTypeExpression) {
            List<Type> recordTypes = new ArrayList<>();
            List<AbstractTypeExpression> expressions = ((RecordTypeExpression) expression).variableDeclarations
                    .stream()
                    .map(d -> d.typeExpression)
                    .toList();

            for (AbstractTypeExpression recordExpressions : expressions) {
                Type recordType = getType(recordExpressions);
                recordTypes.add(recordType);
            }

            Type result = new RecordType(recordTypes);
            return result;
        }

        if (expression instanceof NamedTypeExpression) {
            Symbol symbol = new Symbol(((NamedTypeExpression) expression).identifier.name);
            return new NamedType(symbol);
            // TODO maybe use resolved type instead of Named Type
            // return resolveNamedExpression((NamedTypeExpression) expression);
        }

        throw new UnsupportedOperationException("Expression has unknown Type. Please check implementation. Error at line=" + expression.location);
    }

    /**
     * check the symbolTable for an identifier for a NamedType
     * @param namedTypeExpression
     * @return type of the resolved TypeInfo
     * @throws CompilerError identifier of the NamedType not found in the symbolTable
     */
    private Type resolveNamedExpression(NamedTypeExpression namedTypeExpression) throws CompilerError {
        Info lookup = symbolTable.lookup(new Symbol(namedTypeExpression.identifier.name));
        if (lookup instanceof TypeInfo) {
            return ((TypeInfo) lookup).type;
        }

        throw CompilerError.UnknownTypeReference(namedTypeExpression.location, namedTypeExpression.identifier.name);
    }
}
