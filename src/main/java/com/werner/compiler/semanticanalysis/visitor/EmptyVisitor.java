package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.statements.*;

public class EmptyVisitor implements Visitor {

    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {

    }

    @Override
    public void visit(ProcedureDeclaration functionDeclaration) {

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
    public void visit(ProcedureCall procedureCall) {

    }

    @Override
    public void visit(ReturnStatement returnStatement) {

    }

    @Override
    public void visit(VariableDeclarationStatement variableDeclarationStatement) {

    }

    @Override
    public void visit(WhileStatement whileStatement) {

    }
}
