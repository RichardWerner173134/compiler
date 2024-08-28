package com.werner.compiler.symboltable.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
