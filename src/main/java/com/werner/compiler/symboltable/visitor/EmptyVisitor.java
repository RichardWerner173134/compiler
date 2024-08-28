package com.werner.compiler.symboltable.visitor;

import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.Program;
import com.werner.compiler.ast.declaration.FunctionDeclaration;
import com.werner.compiler.ast.declaration.TypeDeclaration;

public class EmptyVisitor implements Visitor {

    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {

    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {

    }
}
