package com.werner.compiler.semanticanalysis.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
