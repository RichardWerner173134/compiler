package com.werner.compiler.symboltable.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.expressions.type.*;
import com.werner.compiler.ast.statements.Statement;
import com.werner.compiler.exceptions.CompilerError;
import com.werner.compiler.symboltable.Identifier;
import com.werner.compiler.symboltable.Kind;
import com.werner.compiler.symboltable.info.Info;
import com.werner.compiler.symboltable.SymbolTable;
import com.werner.compiler.symboltable.info.TypeInfo;
import com.werner.compiler.symboltable.type.*;

import java.util.ArrayList;
import java.util.List;

public class NameAnalysisVisitor extends EmptyVisitor {
    private final SymbolTable symbolTable;

    public NameAnalysisVisitor() {
        this.symbolTable = new SymbolTable();

        this.symbolTable.enter(new Identifier("int"), new TypeInfo(PrimitiveType.INT_TYPE, Kind.TYPE));
        this.symbolTable.enter(new Identifier("bool"), new TypeInfo(PrimitiveType.BOOLEAN_TYPE, Kind.TYPE));
        this.symbolTable.enter(new Identifier("string"), new TypeInfo(PrimitiveType.STRING_TYPE, Kind.TYPE));
    }

    @Override
    public void visit(Program program) {
        for (Statement statement : program.statementList) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        Type type = getType(functionDeclaration.returnType);

        symbolTable.enter(
                new Identifier(functionDeclaration.identifier.name),
                new TypeInfo(type, Kind.FUNCTION),
                CompilerError.RedeclarationAsType(functionDeclaration.location, functionDeclaration.identifier.name)
        );
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {
        Type type = getType(typeDeclaration.typeExpression);

        typeDeclaration.typeExpression.setSymbolType(type);

        symbolTable.enter(
                new Identifier(typeDeclaration.identifier.name),
                new TypeInfo(type, Kind.TYPE),
                CompilerError.RedeclarationAsType(typeDeclaration.location, typeDeclaration.identifier.name)
        );
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
