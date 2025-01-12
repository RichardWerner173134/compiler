package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.BinaryExpression;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.expressions.FunctionCall;
import com.werner.compiler.ast.expressions.VariableExpression;
import com.werner.compiler.ast.expressions.initializer.ArrayExpression;
import com.werner.compiler.ast.expressions.literals.BooleanLiteral;
import com.werner.compiler.ast.expressions.literals.IntLiteral;
import com.werner.compiler.ast.expressions.literals.StringLiteral;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.ast.expressions.type.PrimitiveTypeExpression;
import com.werner.compiler.ast.expressions.type.RecordTypeExpression;
import com.werner.compiler.ast.statements.*;
import com.werner.compiler.codesynthesis.*;
import com.werner.compiler.codesynthesis.javasynthesis.JavaOperator;
import com.werner.compiler.codesynthesis.javasynthesis.RecordClass;
import com.werner.compiler.codesynthesis.javasynthesis.RecordClassContainer;
import com.werner.compiler.semanticanalysis.Constants;
import com.werner.compiler.semanticanalysis.Symbol;
import com.werner.compiler.semanticanalysis.SymbolTable;
import com.werner.compiler.semanticanalysis.info.FunctionInfo;
import com.werner.compiler.semanticanalysis.info.Info;
import com.werner.compiler.semanticanalysis.info.TypeInfo;
import com.werner.compiler.semanticanalysis.info.VariableInfo;
import com.werner.compiler.semanticanalysis.type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaSynthesisVisitor extends EmptyVisitor {
    public final SymbolTable symbolTable;
    private final CodeEmitter codeEmitter;
    private final RecordClassContainer recordClassContainer;

    private static List<String> classesToAddToMain = new ArrayList();
    private static int recordFieldCounter = 0;
    private static int classCounter = 0;

    public JavaSynthesisVisitor(
            SymbolTable symbolTable,
            CodeEmitter codeEmitter, // share this between Visitors
            RecordClassContainer recordClassContainer // share this between Visitors
    ) {
        this.symbolTable = symbolTable;
        this.codeEmitter = codeEmitter;
        this.recordClassContainer = recordClassContainer;
    }

    @Override
    public void visit(Program program) {
        codeEmitter.emit("public class Main {");

        for (Statement statement : program.statementList) {
            transformRecordTypeDeclarationToClass(statement);
        }

        for (Statement statement : program.statementList) {

            Symbol symbol = new Symbol(String.valueOf(statement.hashCode()));
            SymbolTable innerSymbolTable = symbolTable.innerScopes.get().get(symbol);

            JavaSynthesisVisitor innerVisitor = new JavaSynthesisVisitor(innerSymbolTable, codeEmitter, recordClassContainer);
            statement.accept(innerVisitor);
        }

        classesToAddToMain.forEach(codeEmitter::emit);

        codeEmitter.emit("}");
        codeEmitter.print();
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        Symbol symbol = new Symbol(functionDeclaration.identifier.name);
        Info functionSymbol = symbolTable.lookup(symbol);

        Type returnType = ((FunctionInfo) functionSymbol).type;

        String functionReturnType;
        if (returnType instanceof RecordType) {
            List<VariableDeclaration> variableDeclarations = ((RecordTypeExpression) functionDeclaration.returnType).variableDeclarations;
            functionReturnType = getJavaTypeName(((FunctionInfo) functionSymbol).type, Optional.of(variableDeclarations));
        } else {
            functionReturnType = getJavaTypeName(((FunctionInfo) functionSymbol).type, Optional.empty());
        }

        String signature = Constants.PUBLIC + " " +
                Constants.STATIC + " " +
                functionReturnType + " " +
                functionDeclaration.identifier.name + "(";

        codeEmitter.emit(signature);

        String parameterList = functionDeclaration.parametersList
                .stream()
                .map(variableDeclaration -> {
                    Symbol variableSymbol = new Symbol(variableDeclaration.identifier.name);
                    Info info = symbolTable.lookup(variableSymbol);
                    Type type = ((VariableInfo) info).type;

                    String javaTypeName = getJavaTypeName(type, Optional.empty());
                    return javaTypeName + " " + variableDeclaration.identifier.name;
                })
                .collect(Collectors.joining(", "));

        codeEmitter.emit(parameterList + ") {");

        for (Statement statement : functionDeclaration.statementList) {
            statement.accept(this);
        }

        codeEmitter.emit("}");
    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {
        String signature = Constants.PUBLIC + " " +
                Constants.STATIC + " " +
                Constants.VOID + " " +
                procedureDeclaration.identifier.name + "(";

        codeEmitter.emit(signature);

        String parameterList = procedureDeclaration.parametersList
                .stream()
                .map(variableDeclaration -> {
                    Symbol variableSymbol = new Symbol(variableDeclaration.identifier.name);
                    Info info = symbolTable.lookup(variableSymbol);
                    Type type = ((VariableInfo) info).type;

                    String javaTypeName = getJavaTypeName(type, Optional.empty());
                    return javaTypeName + " " + variableDeclaration.identifier.name;
                })
                .collect(Collectors.joining(", "));

        codeEmitter.emit(parameterList + ") {");

        for (Statement statement : procedureDeclaration.statementList) {
            statement.accept(this);
        }

        codeEmitter.emit("}");
    }

    @Override
    public void visit(VariableDeclarationStatement variableDeclarationStatement) {
        Symbol symbol = new Symbol(variableDeclarationStatement.variableDeclaration.identifier.name);
        Info info = symbolTable.lookup(symbol);

        String javaTypeName;
        if (variableDeclarationStatement.variableDeclaration.typeExpression instanceof RecordTypeExpression) {

            RecordType recordType = (RecordType) ((VariableInfo) info).type;

            javaTypeName = addClassDeclaration(
                    recordType,
                    Optional.of(variableDeclarationStatement.variableDeclaration.identifier.name),
                    Optional.of(((RecordTypeExpression) variableDeclarationStatement.variableDeclaration.typeExpression).variableDeclarations)
            );
        } else {
            javaTypeName = getJavaTypeName(((VariableInfo) info).type, Optional.empty());
        }

        String declaration = javaTypeName + " " + variableDeclarationStatement.variableDeclaration.identifier.name + ";";
        codeEmitter.emit(declaration);
    }

    @Override
    public void visit(FunctionCall functionCall) {
        codeEmitter.emit(functionCall.identifier.name + "(");

        for (Expression expression : functionCall.argumentList) {
            expression.accept(this);
        }

        codeEmitter.emit(")");
    }

    @Override
    public void visit(AssignStatement assignStatement) {
        String javaAssignment = assignStatement.target.name + " = ";
        codeEmitter.emit(javaAssignment);

        assignStatement.value.accept(this);

        codeEmitter.emit(";");
    }

    @Override
    public void visit(IfStatement ifStatement) {
        codeEmitter.emit("if(");

        ifStatement.condition.accept(this);

        codeEmitter.emit(") {");

        Symbol symbolIf = new Symbol(String.valueOf(ifStatement.ifStatements.hashCode()));
        SymbolTable innerSymbolTableIf = symbolTable.innerScopes.get().get(symbolIf);

        JavaSynthesisVisitor innerVisitorIf = new JavaSynthesisVisitor(innerSymbolTableIf, codeEmitter, recordClassContainer);
        for (Statement statementIf : ifStatement.ifStatements) {
            statementIf.accept(innerVisitorIf);
        }

        codeEmitter.emit("} else {");

        Symbol symbolElse = new Symbol(String.valueOf(ifStatement.elseStatements.hashCode()));
        SymbolTable innerSymbolTableElse = symbolTable.innerScopes.get().get(symbolElse);

        JavaSynthesisVisitor innerVisitorElse = new JavaSynthesisVisitor(innerSymbolTableElse, codeEmitter, recordClassContainer);
        for (Statement statementElse : ifStatement.elseStatements.orElse(Collections.emptyList())) {
            statementElse.accept(innerVisitorElse);
        }

        codeEmitter.emit("}");
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        codeEmitter.emit("while(");

        whileStatement.condition.accept(this);

        codeEmitter.emit(") {");

        Symbol symbolWhile = new Symbol(String.valueOf(whileStatement.statementList.hashCode()));
        SymbolTable innerSymbolTableWhile = symbolTable.innerScopes.get().get(symbolWhile);

        JavaSynthesisVisitor innerVisitorWhile = new JavaSynthesisVisitor(innerSymbolTableWhile, codeEmitter, recordClassContainer);
        for (Statement statement : whileStatement.statementList) {
            statement.accept(innerVisitorWhile);
        }

        codeEmitter.emit("}");
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        codeEmitter.emit("(");
        binaryExpression.leftOperand.accept(this);

        JavaOperator javaOperator = JavaOperator.fromAstOperator(binaryExpression.operator);

        codeEmitter.emit(" " + javaOperator.value + " ");

        binaryExpression.rightOperand.accept(this);
        codeEmitter.emit(")");
    }

    @Override
    public void visit(TypedReturnStatement typedReturnStatement) {
        codeEmitter.emit("return ");
        typedReturnStatement.expression.accept(this);
        codeEmitter.emit(";");
    }

    @Override
    public void visit(EmptyReturnStatement emptyReturnStatement) {
        codeEmitter.emit("return;");
    }

    @Override
    public void visit(VariableExpression variableExpression) {
        codeEmitter.emit(variableExpression.identifier.name);
    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        codeEmitter.emit(String.valueOf(booleanLiteral.value));
    }

    @Override
    public void visit(StringLiteral stringLiteral) {
        codeEmitter.emit("\"" + stringLiteral.value + "\"");
    }

    @Override
    public void visit(IntLiteral intLiteral) {
        codeEmitter.emit(String.valueOf(intLiteral.value));
    }

    @Override
    public void visit(ArrayExpression arrayExpression) {
        String javaTypeExpression = getJavaTypeExpression(arrayExpression.typeExpression);
        codeEmitter.emit("new " + javaTypeExpression);

        for (Expression indexSize : arrayExpression.indexSizes) {
            codeEmitter.emit("[");
            indexSize.accept(this);
            codeEmitter.emit("]");
        }
    }

    public void transformRecordTypeDeclarationToClass(Statement statement) {
        if (statement instanceof TypeDeclaration == false) {
            return;
        }

        TypeDeclaration typeDeclaration = (TypeDeclaration) statement;

        if (typeDeclaration.typeExpression instanceof RecordTypeExpression == false) {
            return;
        }

        Symbol symbol = new Symbol(typeDeclaration.identifier.name);
        Info info = symbolTable.lookup(symbol);

        RecordType recordType = (RecordType) ((TypeInfo) info).type;

        addClassDeclaration(
                recordType,
                Optional.of(typeDeclaration.identifier.name),
                Optional.of(((RecordTypeExpression) typeDeclaration.typeExpression).variableDeclarations)
        );
    }

    private String addClassDeclaration(RecordType recordType, Optional<String> name, Optional<List<VariableDeclaration>> variableDeclarations) {
        List<Type> recordTypes = recordType.recordTypes;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < recordTypes.size(); i++) {
            Type type = recordTypes.get(i);

            String fieldName = variableDeclarations.isPresent()
                    ? variableDeclarations.get().get(i).identifier.name
                    : getNewFieldName();

            String innerClassName;
            if (variableDeclarations.isPresent()
                    && variableDeclarations.get().get(i).typeExpression instanceof RecordTypeExpression) {

                VariableDeclaration variableDeclaration = variableDeclarations.get().get(i);

                innerClassName = addClassDeclaration(
                        (RecordType) type,
                        Optional.of(variableDeclaration.identifier.name),
                        Optional.of(((RecordTypeExpression) variableDeclaration.typeExpression).variableDeclarations
                ));
            } else {
                innerClassName = getJavaTypeName(type, Optional.empty());
            }

            String s = "public " + innerClassName + " " + fieldName + ";";
            list.add(s);
        }

        Optional<RecordClass> existingClass = recordClassContainer.get(recordType);

        String newClassName;
        if (existingClass.isEmpty()) {
            RecordClass addedClass = recordClassContainer.add(recordType, name);
            newClassName = addedClass.generatedClassName;

            String classDeclaration = "class " + newClassName + "{";
            classDeclaration += String.join("", list);
            classDeclaration += "}";

            classesToAddToMain.add(classDeclaration);
        } else {
            newClassName = existingClass.get().generatedClassName;
        }

        return newClassName;
    }

    private String getJavaTypeName(Type type, Optional<List<VariableDeclaration>> variableDeclarations) {
        if (type instanceof PrimitiveType) {
            if (type.equals(PrimitiveType.INT_TYPE)) {
                return "int";
            }
            if (type.equals(PrimitiveType.STRING_TYPE)) {
                return "String";
            }
            if (type.equals(PrimitiveType.BOOLEAN_TYPE)) {
                return "boolean";
            }
        }

        if (type instanceof ArrayType) {
            String baseType = getJavaTypeName(((ArrayType) type).baseType, Optional.empty());
            return baseType + "[]";
        }

        if (type instanceof NamedType) {
            String identifier = ((NamedType) type).identifier.identifier;

            Symbol symbol = new Symbol(identifier);
            Info info = symbolTable.lookup(symbol);

            if (((TypeInfo)info).type instanceof RecordType) {
                Optional<RecordClass> recordClass = recordClassContainer.get((RecordType) ((TypeInfo) info).type);
                return recordClass.get().generatedClassName;
            } else {
                return getJavaTypeName(((TypeInfo) info).type, Optional.empty());
            }
        }

        if (type instanceof RecordType) {
            return addClassDeclaration((RecordType) type, Optional.empty(), variableDeclarations);
        }

        throw new IllegalArgumentException("whopsie");
    }

    private String getJavaTypeExpression(AbstractTypeExpression abstractTypeExpression) {
        if (abstractTypeExpression instanceof PrimitiveTypeExpression) {
            return switch (((PrimitiveTypeExpression) abstractTypeExpression).primitiveType) {
                case INTEGER -> "int";
                case STRING -> "String";
                case BOOLEAN -> "boolean";
            };
        }

        throw new IllegalArgumentException("whopsie");
    }

    private static String capitalizeString(String identifier) {
        return identifier.substring(0, 1).toUpperCase() + identifier.substring(1);
    }

    private static String getNewFieldName() {
        int suffix = ++recordFieldCounter;
        return "field" + suffix;
    }
}
