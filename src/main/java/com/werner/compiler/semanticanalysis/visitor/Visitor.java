package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.BinaryExpression;
import com.werner.compiler.ast.expressions.FunctionCall;
import com.werner.compiler.ast.expressions.type.ArrayTypeExpression;
import com.werner.compiler.ast.expressions.type.NamedTypeExpression;
import com.werner.compiler.ast.expressions.type.PrimitiveTypeExpression;
import com.werner.compiler.ast.expressions.type.RecordTypeExpression;
import com.werner.compiler.ast.statements.*;

public interface Visitor {
    void visit(Program program);
    void visit(FunctionDeclaration functionDeclaration);
    void visit(ProcedureDeclaration procedureDeclaration);
    void visit(TypeDeclaration typeDeclaration);
    void visit(VariableDeclaration variableDeclaration);

    void visit(AssignStatement assignStatement);
    void visit(EmptyStatement emptyStatement);
    void visit(IfStatement ifStatement);
    void visit(TypedReturnStatement typedReturnStatement);
    void visit(EmptyReturnStatement emptyReturnStatement);
    void visit(VariableDeclarationStatement variableDeclarationStatement);
    void visit(WhileStatement whileStatement);

    void visit(FunctionCall functionCall);
    void visit(ProcedureCall procedureCall);

    void visit(ArrayTypeExpression arrayTypeExpression);
    void visit(PrimitiveTypeExpression primitiveTypeExpression);
    void visit(RecordTypeExpression recordTypeExpression);
    void visit(NamedTypeExpression namedTypeExpression);

    void visit(BinaryExpression binaryExpression);
}
