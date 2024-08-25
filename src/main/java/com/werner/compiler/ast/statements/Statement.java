package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.Node;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Statement extends Node {
    public Statement(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
