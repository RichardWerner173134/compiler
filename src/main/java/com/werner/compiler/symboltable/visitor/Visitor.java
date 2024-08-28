package com.werner.compiler.symboltable.visitor;

import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;

public interface Visitor {
    void visit(Program program);
    void visit(FunctionDeclaration functionDeclaration);
    void visit(TypeDeclaration typeDeclaration);
}
