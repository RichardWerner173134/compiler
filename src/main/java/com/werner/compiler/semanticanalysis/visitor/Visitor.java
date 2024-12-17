package com.werner.compiler.semanticanalysis.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.ProcedureDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;
import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.statements.*;

public interface Visitor {
    void visit(Program program);
    void visit(FunctionDeclaration functionDeclaration);
    void visit(ProcedureDeclaration functionDeclaration);
    void visit(TypeDeclaration typeDeclaration);
    void visit(VariableDeclaration variableDeclaration);

    void visit(AssignStatement assignStatement);
    void visit(EmptyStatement emptyStatement);
    void visit(IfStatement ifStatement);
    void visit(ProcedureCall procedureCall);
    void visit(ReturnStatement returnStatement);
    void visit(VariableDeclarationStatement variableDeclarationStatement);
    void visit(WhileStatement whileStatement);
}
