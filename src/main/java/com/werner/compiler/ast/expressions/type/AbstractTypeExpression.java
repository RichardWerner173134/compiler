package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.expressions.Expression;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class AbstractTypeExpression extends Expression {

    public AbstractTypeExpression(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
