package com.werner.compiler.ast.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
