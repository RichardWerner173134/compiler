package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.PrimitiveType;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Expression extends Node {

    public Expression(
            ComplexSymbolFactory.Location location
    ) {
        super(location);
    }
}
