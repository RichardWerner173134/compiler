package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.expressions.BinaryExpression;
import com.werner.compiler.ast.expressions.FunctionCall;
import com.werner.compiler.ast.expressions.VariableExpression;
import com.werner.compiler.ast.expressions.initializer.ArrayExpression;
import com.werner.compiler.ast.expressions.literals.BooleanLiteral;
import com.werner.compiler.ast.expressions.literals.IntLiteral;
import com.werner.compiler.ast.expressions.literals.StringLiteral;
import com.werner.compiler.ast.expressions.type.ArrayTypeExpression;
import com.werner.compiler.ast.expressions.type.NamedTypeExpression;
import com.werner.compiler.ast.expressions.type.PrimitiveTypeExpression;
import com.werner.compiler.ast.expressions.type.RecordTypeExpression;
import com.werner.compiler.ast.statements.*;

public class EmptyVisitor implements Visitor {

    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {

    }

    @Override
    public void visit(ProcedureDeclaration procedureDeclaration) {

    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {

    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {

    }

    @Override
    public void visit(AssignStatement assignStatement) {

    }

    @Override
    public void visit(EmptyStatement emptyStatement) {

    }

    @Override
    public void visit(IfStatement ifStatement) {

    }

    @Override
    public void visit(TypedReturnStatement typedReturnStatement) {

    }

    @Override
    public void visit(EmptyReturnStatement emptyReturnStatement) {

    }

    @Override
    public void visit(VariableDeclarationStatement variableDeclarationStatement) {

    }

    @Override
    public void visit(WhileStatement whileStatement) {

    }

    @Override
    public void visit(FunctionCall functionCall) {

    }

    @Override
    public void visit(ProcedureCall procedureCall) {

    }

    @Override
    public void visit(ArrayTypeExpression arrayTypeExpression) {

    }

    @Override
    public void visit(PrimitiveTypeExpression primitiveTypeExpression) {

    }

    @Override
    public void visit(RecordTypeExpression recordTypeExpression) {

    }

    @Override
    public void visit(NamedTypeExpression namedTypeExpression) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {

    }

    @Override
    public void visit(VariableExpression variableExpression) {

    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {

    }

    @Override
    public void visit(StringLiteral stringLiteral) {

    }

    @Override
    public void visit(IntLiteral intLiteral) {

    }

    @Override
    public void visit(ArrayExpression arrayExpression) {

    }


}
