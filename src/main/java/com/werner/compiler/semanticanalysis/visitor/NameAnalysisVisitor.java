package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.type.*;
import com.werner.compiler.ast.statements.*;
import com.werner.compiler.exceptions.CompilerError;
import com.werner.compiler.semanticanalysis.Identifier;
import com.werner.compiler.semanticanalysis.Kind;
import com.werner.compiler.semanticanalysis.info.Info;
import com.werner.compiler.semanticanalysis.SymbolTable;
import com.werner.compiler.semanticanalysis.info.ProcedureInfo;
import com.werner.compiler.semanticanalysis.info.TypeInfo;
import com.werner.compiler.semanticanalysis.info.VariableInfo;
import com.werner.compiler.semanticanalysis.type.ArrayType;
import com.werner.compiler.semanticanalysis.type.PrimitiveType;
import com.werner.compiler.semanticanalysis.type.RecordType;
import com.werner.compiler.semanticanalysis.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class NameAnalysisVisitor extends EmptyVisitor {
    private final SymbolTable symbolTable;

    public NameAnalysisVisitor() {
        this.symbolTable = new SymbolTable();

        this.symbolTable.enter(new Identifier("int"), new TypeInfo(PrimitiveType.INT_TYPE, Kind.TYPE));
        this.symbolTable.enter(new Identifier("bool"), new TypeInfo(PrimitiveType.BOOLEAN_TYPE, Kind.TYPE));
        this.symbolTable.enter(new Identifier("string"), new TypeInfo(PrimitiveType.STRING_TYPE, Kind.TYPE));
    }

    public NameAnalysisVisitor(SymbolTable outerSymbolTable) {
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
        if (this.symbolTable.outerScope.isPresent()) {
            throw CompilerError.InvalidDeclarationLocation(functionDeclaration.location, functionDeclaration.identifier.name);
        }

        Type type = getType(functionDeclaration.returnType);

        symbolTable.enter(
                new Identifier(functionDeclaration.identifier.name),
                new TypeInfo(type, Kind.FUNCTION),
                CompilerError.RedeclarationOfType(functionDeclaration.location, functionDeclaration.identifier.name)
        );

        NameAnalysisVisitor innerVisitor = new NameAnalysisVisitor(this.symbolTable);

        for (VariableDeclaration argument : functionDeclaration.parametersList) {
            innerVisitor.symbolTable.enter(
                    new Identifier(argument.identifier.name),
                    new VariableInfo(false), // TODO implement refs
                    CompilerError.RedeclarationOfType(argument.location, argument.identifier.name)
            );
        }

        for (Statement statement : functionDeclaration.statementList) {
            statement.accept(innerVisitor);
        }
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
        // allow functions only in global scope
        if (this.symbolTable.outerScope.isPresent()) {
            throw CompilerError.InvalidDeclarationLocation(procedureDeclaration.location, procedureDeclaration.identifier.name);
        }

        symbolTable.enter(
                new Identifier(procedureDeclaration.identifier.name),
                new ProcedureInfo(), // TODO this holds no information. maybe remove
                CompilerError.RedeclarationOfType(procedureDeclaration.location, procedureDeclaration.identifier.name)
        );

        NameAnalysisVisitor innerVisitor = new NameAnalysisVisitor(this.symbolTable);

        for (VariableDeclaration argument : procedureDeclaration.parametersList) {
            innerVisitor.symbolTable.enter(
                    new Identifier(argument.identifier.name),
                    new VariableInfo(false), // TODO implement refs
                    CompilerError.RedeclarationOfType(argument.location, argument.identifier.name)
            );
        }

        for (Statement statement : procedureDeclaration.statementList) {
            statement.accept(innerVisitor);
        }
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {
        // allow type declarations only in global scope
        if (this.symbolTable.outerScope.isPresent()) {
            throw CompilerError.InvalidDeclarationLocation(typeDeclaration.location, typeDeclaration.identifier.name);
        }

        Type type = getType(typeDeclaration.typeExpression);

        typeDeclaration.typeExpression.setSymbolType(type); // TODO ?

        symbolTable.enter(
                new Identifier(typeDeclaration.identifier.name),
                new TypeInfo(type, Kind.TYPE),
                CompilerError.RedeclarationOfType(typeDeclaration.location, typeDeclaration.identifier.name)
        );
    }

    @Override
    public void visit(VariableDeclarationStatement variableDeclarationStatement) {
        // allow type declarations only in local scope
        if (this.symbolTable.outerScope.isEmpty()) {
            throw CompilerError.InvalidDeclarationLocation(variableDeclarationStatement.location, variableDeclarationStatement.variableDeclaration.identifier.name);
        }

        this.symbolTable.enter(
                new Identifier(variableDeclarationStatement.variableDeclaration.identifier.name),
                new VariableInfo(false),
                CompilerError.RedeclarationOfVariable(variableDeclarationStatement.variableDeclaration.location, variableDeclarationStatement.variableDeclaration.identifier.name)
        );
    }

    @Override
    public void visit(IfStatement ifStatement) {
        NameAnalysisVisitor innerVisitorIf = new NameAnalysisVisitor(this.symbolTable);
        for (Statement statement : ifStatement.ifStatements) {
            statement.accept(innerVisitorIf);
        }

        if (ifStatement.elseStatements.isEmpty()) {
            return;
        }

        NameAnalysisVisitor innerVisitorElse = new NameAnalysisVisitor(this.symbolTable);
        for (Statement statement : ifStatement.elseStatements.get()) {
            statement.accept(innerVisitorElse);
        }
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        NameAnalysisVisitor innerVisitor = new NameAnalysisVisitor(this.symbolTable);
        for (Statement statement : whileStatement.statementList) {
            statement.accept(innerVisitor);
        }
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
            return resolveNamedExpression((NamedTypeExpression) expression);
        }

        throw new UnsupportedOperationException("Expression has unknown Type. Please check implementation. Error at line=" + expression.location);
    }

    /**
     *
     * check the symbolTable for an identifier for a NamedType
     * @param namedTypeExpression
     * @return type of the resolved TypeInfo
     * @throws CompilerError identifier of the NamedType not found in the symbolTable
     */
    private Type resolveNamedExpression(NamedTypeExpression namedTypeExpression) throws CompilerError {
        Info lookup = symbolTable.lookup(new Identifier(namedTypeExpression.identifier.name));
        if (lookup instanceof TypeInfo) {
            return ((TypeInfo) lookup).type;
        }

        throw CompilerError.UnknownTypeReference(namedTypeExpression.location, namedTypeExpression.identifier.name);
    }
}
