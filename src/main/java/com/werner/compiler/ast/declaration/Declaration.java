package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Node;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Declaration extends Node {

    public Declaration(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
