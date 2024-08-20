package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class TypeExpression extends Expression {


    public TypeExpression(
            ComplexSymbolFactory.Location location
    ) {
        super(location);
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
