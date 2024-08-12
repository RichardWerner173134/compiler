package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.Type;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Expression extends Node {

    public final Type dataType;

    public Expression(
            ComplexSymbolFactory.Location location,
            Type dataType
    ) {
        super(location);

        this.dataType = dataType;
    }
}
