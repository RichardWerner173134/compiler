package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.*;
import com.werner.compiler.ast.expressions.initializer.ArrayExpression;
import com.werner.compiler.ast.expressions.literals.BooleanLiteral;
import com.werner.compiler.ast.expressions.literals.IntLiteral;
import com.werner.compiler.ast.expressions.literals.StringLiteral;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.ast.expressions.type.ArrayTypeExpression;
import com.werner.compiler.ast.expressions.type.NamedTypeExpression;
import com.werner.compiler.ast.expressions.type.PrimitiveTypeExpression;
import com.werner.compiler.ast.statements.*;
import com.werner.compiler.exceptions.CompilerError;
import com.werner.compiler.semanticanalysis.Kind;
import com.werner.compiler.semanticanalysis.Symbol;
import com.werner.compiler.semanticanalysis.SymbolTable;
import com.werner.compiler.semanticanalysis.info.FunctionInfo;
import com.werner.compiler.semanticanalysis.info.Info;
import com.werner.compiler.semanticanalysis.info.ProcedureInfo;
import com.werner.compiler.semanticanalysis.info.VariableInfo;
import com.werner.compiler.semanticanalysis.type.ArrayType;
import com.werner.compiler.semanticanalysis.type.NamedType;
import com.werner.compiler.semanticanalysis.type.PrimitiveType;
import com.werner.compiler.semanticanalysis.type.Type;

import java.util.List;
import java.util.Optional;

public class TypeAnalysisVisitor extends EmptyVisitor {
    private final SymbolTable symbolTable;

    private final static String MAIN_PROCEDURE_NAME = "main";

    public TypeAnalysisVisitor(
            SymbolTable symbolTable
    ) {
        this.symbolTable = new SymbolTable(symbolTable);
    }

    @Override
    public void visit(Program program) {
        Symbol symbol = new Symbol(MAIN_PROCEDURE_NAME);
        Info info = symbolTable.lookup(symbol);

        if (info == null) {
            throw CompilerError.MainProcedureMissing();
        }

        if (info.getKind() != Kind.PROCEDURE) {
            throw CompilerError.MainProcedureArgumentCountNotZero(info.getKind().name());
        }

        int mainProcedureCount = ((ProcedureInfo) info).parameters.size();

        if (mainProcedureCount != 0) {
            throw CompilerError.MainProcedureArgumentCountNotZero(mainProcedureCount);
        }
    }

    @Override
    public void visit(AssignStatement assignStatement) {
        Symbol key = new Symbol(assignStatement.target.name);
        Info info = this.symbolTable.lookup(key);

        // symbol table contains an element
        if (info == null) {
            throw CompilerError.UnknownIdentifier(assignStatement.location, key.identifier);
        }

        // and its an variable
        if (info.getKind() != Kind.VARIABLE) {
            throw CompilerError.NotAVariable(assignStatement.location, assignStatement.target.name);
        }

        Type declaredType = ((VariableInfo) info).type;
        Type assignmentType = getType(assignStatement.value);

        if (!declaredType.equals(assignmentType)) {
            throw CompilerError.TypeError(assignStatement.location, declaredType.toString(), assignmentType.toString());
        }
    }

    @Override
    public void visit(TypedReturnStatement typedReturnStatement) {
        typedReturnStatement.expression.accept(this);
    }

    @Override
    public void visit(IfStatement ifStatement) {
        ifStatement.condition.accept(this);
        Type conditionType = getType(ifStatement.condition);

        if (!conditionType.equals(PrimitiveType.BOOLEAN_TYPE)) {
            throw CompilerError.TypeErrorCondition(ifStatement.condition.location, conditionType.toString());
        }
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        whileStatement.condition.accept(this);
        Type conditionType = getType(whileStatement.condition);

        if (!conditionType.equals(PrimitiveType.BOOLEAN_TYPE)) {
            throw CompilerError.TypeErrorCondition(whileStatement.condition.location, conditionType.toString());
        }
    }

    @Override
    public void visit(FunctionCall functionCall) {
        Symbol symbol = new Symbol(functionCall.identifier.name);
        Info info = symbolTable.lookup(symbol);

        if (info == null) {
            throw CompilerError.UnknownIdentifier(functionCall.location, functionCall.identifier.name);
        }

        if (info.getKind() != Kind.FUNCTION) {
            throw CompilerError.NotAFunction(functionCall.location, functionCall.identifier.name);
        }

        List<VariableInfo> parameters = ((FunctionInfo) info).parameters;
        boolean argumentCountMatches = parameters.size() == functionCall.argumentList.size();
        if (!argumentCountMatches) {
            throw CompilerError.InvalidArgumentCount(functionCall.location, functionCall.argumentList.size());
        }

        for (int i = 0; i < parameters.size(); i++) {
            VariableInfo parameter = parameters.get(i);

            Expression argument = functionCall.argumentList.get(i);
            Type argumentType = getType(argument);

            if (!argumentType.equals(parameter.type)) {
                throw CompilerError.TypeErrorFunctionCall(functionCall.location, parameter.type.toString(), argumentType.toString(), i);
            }
        }
    }

    @Override
    public void visit(ProcedureCall procedureCall) {
        Symbol key = new Symbol(procedureCall.identifier.name);
        Info info = this.symbolTable.lookup(key);
        if (info == null) {
            throw CompilerError.UnknownIdentifier(procedureCall.location, key.identifier);
        }

        if (info.getKind() != Kind.PROCEDURE) {
            throw CompilerError.NotAFunction(procedureCall.location, procedureCall.identifier.name);
        }

        List<VariableInfo> parameters = ((ProcedureInfo) info).parameters;

        boolean argumentCountMatches = parameters.size() == procedureCall.argumentList.size();
        if (!argumentCountMatches) {
            throw CompilerError.InvalidArgumentCount(procedureCall.location, procedureCall.argumentList.size());
        }

        for (int i = 0; i < parameters.size(); i++) {
            VariableInfo parameter = parameters.get(i);

            Expression argument = procedureCall.argumentList.get(i);
            Type argumentType = getType(argument);

            if (!argumentType.equals(parameter.type)) {
                throw CompilerError.TypeErrorProcedureCall(procedureCall.location, parameter.type.toString(), argumentType.toString(), i);
            }
        }
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        variableDeclaration.typeExpression.accept(this);
    }

    @Override
    public void visit(ArrayTypeExpression arrayTypeExpression) {
        arrayTypeExpression.typeExpression.accept(this);
    }

    @Override
    public void visit(PrimitiveTypeExpression primitiveTypeExpression) {
        Symbol key = new Symbol(primitiveTypeExpression.primitiveType.toString());
        Info info = this.symbolTable.lookup(key);
        if (info == null) {
            throw CompilerError.UnknownTypeReference(primitiveTypeExpression.location, key.identifier);
        }
    }

    @Override
    public void visit(NamedTypeExpression namedTypeExpression) {
        Symbol key = new Symbol(namedTypeExpression.identifier.name);
        Info info = this.symbolTable.lookup(key);
        if (info == null) {
            throw CompilerError.UnknownTypeReference(namedTypeExpression.location, key.identifier);
        }
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Type leftOperandType = getType(binaryExpression.leftOperand);
        Type rightOperandType = getType(binaryExpression.rightOperand);

        if (binaryExpression.operator.isArithmetic()) {
            boolean isValidArithmeticExpression = leftOperandType.equals(PrimitiveType.INT_TYPE)
                    && rightOperandType.equals(PrimitiveType.INT_TYPE);

            if (!isValidArithmeticExpression) {
                throw CompilerError.TypeErrorArithmeticExpression(binaryExpression.location, leftOperandType.toString(), rightOperandType.toString());
            }
        }

        else if (binaryExpression.operator.isComparison()) {
            boolean isEqualityComparison = List.of(Operator.EQ, Operator.NEQ).contains(binaryExpression.operator);

            boolean isValidIntegerComparison = leftOperandType.equals(PrimitiveType.INT_TYPE) && rightOperandType.equals(PrimitiveType.INT_TYPE);

            if (isEqualityComparison && !leftOperandType.equals(rightOperandType)) {
                throw CompilerError.TypeErrorEqualityComparisonExpression(binaryExpression.location, leftOperandType.toString(), rightOperandType.toString());
            } else if (!isValidIntegerComparison) {
                throw CompilerError.TypeErrorIntegerComparison(binaryExpression.location, leftOperandType.toString(), rightOperandType.toString());
            }
        }
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        // return type
        Type functionReturnType = getType(functionDeclaration.returnType);

        functionDeclaration.statementList
                .stream()
                .filter(s -> s instanceof EmptyReturnStatement)
                .forEach(s -> {
                    throw CompilerError.NoEmptyReturnAllowed(s.location);
                });

        // return statements match return type
        functionDeclaration.statementList.stream()
                .filter(s -> s instanceof TypedReturnStatement)
                .forEach(s -> {
                    Type returnStatementType = getType(((TypedReturnStatement) s).expression);

                    if (!returnStatementType.equals(functionReturnType)) {
                        throw CompilerError.TypeError(s.location, functionReturnType.toString(), returnStatementType.toString());
                    }
                });

        // every path has a return statement
        boolean returnsOnAllPaths = functionDeclaration.statementList
                .stream()
                .anyMatch(this::containsReturnStatementForAllPaths);

        if (!returnsOnAllPaths) {
            throw CompilerError.MissingReturnStatement(functionDeclaration.location, functionDeclaration.identifier.name);
        }
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
        procedureDeclaration.statementList.stream()
                .filter(s -> s instanceof TypedReturnStatement)
                .forEach(s -> {
                    throw CompilerError.NoTypedReturnAllowed(s.location);
                });
    }

    private Type getType(Expression expression) {
        if (expression instanceof IntLiteral) {
            return PrimitiveType.INT_TYPE;
        }

        if (expression instanceof StringLiteral) {
            return PrimitiveType.STRING_TYPE;
        }

        if (expression instanceof BooleanLiteral) {
            return PrimitiveType.BOOLEAN_TYPE;
        }

        if (expression instanceof ArrayExpression) {
            Expression indexSize = ((ArrayExpression) expression).indexSize;
            Type indexType = getType(indexSize);

            if (indexType != PrimitiveType.INT_TYPE) {
                throw CompilerError.ArrayInitializationError(expression.location);
            }

            AbstractTypeExpression typeExpression = ((ArrayExpression) expression).typeExpression;
            Type expressionType = getType(typeExpression);

            return new ArrayType(expressionType);
        }

        if (expression instanceof PrimitiveTypeExpression) {
            return switch (((PrimitiveTypeExpression) expression).primitiveType) {
                case INTEGER -> PrimitiveType.INT_TYPE;
                case STRING -> PrimitiveType.STRING_TYPE;
                case BOOLEAN -> PrimitiveType.BOOLEAN_TYPE;
            };
        }

        if (expression instanceof NamedTypeExpression) {
            Symbol key = new Symbol(((NamedTypeExpression) expression).identifier.name);
            Info info = this.symbolTable.lookup(key);
            if (info == null) {
                throw CompilerError.UnknownTypeReference(expression.location, key.identifier);
            }

            if (info.getKind() != Kind.TYPE) {
                throw CompilerError.NotAType(expression.location, ((NamedTypeExpression) expression).identifier.name);
            }

            return new NamedType(key);
        }

        if (expression instanceof BinaryExpression) {
            boolean isArithmetic = ((BinaryExpression) expression).operator.isArithmetic();

            if (isArithmetic) {
                expression.accept(this);
                return PrimitiveType.INT_TYPE;
            } else if (((BinaryExpression) expression).operator.isComparison()){
                expression.accept(this);
                return PrimitiveType.BOOLEAN_TYPE;
            }
        }

        if (expression instanceof VariableExpression) {
            Symbol symbol = new Symbol(((VariableExpression) expression).identifier.name);
            Info info = symbolTable.lookup(symbol);

            if (info == null) {
                throw CompilerError.UnknownTypeReference(expression.location, symbol.identifier);
            }

            if (info.getKind() != Kind.VARIABLE) {
                throw CompilerError.NotAVariable(expression.location, symbol.identifier);
            }

            return ((VariableInfo)info).type;
        }

        if (expression instanceof FunctionCall) {
            Symbol symbol = new Symbol(((FunctionCall) expression).identifier.name);
            Info info = symbolTable.lookup(symbol);

            if (info == null) {
                throw CompilerError.UnknownIdentifier(expression.location, symbol.identifier);
            }

            if (info.getKind() != Kind.FUNCTION) {
                throw CompilerError.NotAFunction(expression.location, ((FunctionCall) expression).identifier.name);
            }

            return ((FunctionInfo)info).type;
        }

        throw new UnsupportedOperationException("Expression has unknown Type. Please check implementation. Error at line=" + expression.location);
    }

    private boolean containsReturnStatementForAllPaths(Statement statement) {
        if (statement instanceof TypedReturnStatement) {
            return true;
        }

        if (statement instanceof IfStatement) {
            Optional<List<Statement>> elseStatements = ((IfStatement) statement).elseStatements;
            if (elseStatements.isEmpty()) {
                return false;
            }

            boolean ifContainsAtLeastOneReturn = ((IfStatement) statement).ifStatements
                    .stream()
                    .anyMatch(this::containsReturnStatementForAllPaths);


            boolean elseContainsAtLeastOneReturn = elseStatements
                    .get()
                    .stream()
                    .anyMatch(this::containsReturnStatementForAllPaths);

            return ifContainsAtLeastOneReturn && elseContainsAtLeastOneReturn;
        }

        return false;
    }
}
