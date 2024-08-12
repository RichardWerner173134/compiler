package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class Statement extends Node {
    public Statement(ComplexSymbolFactory.Location location) {
        super(location);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit();
    }


}
