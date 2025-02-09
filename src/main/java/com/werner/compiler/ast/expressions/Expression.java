package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Node;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Expression extends Node {
    public String tempVariable;

    public Expression(
            ComplexSymbolFactory.Location location
    ) {
        super(location);
    }
}
