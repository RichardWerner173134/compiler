package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class BooleanLiteral extends Expression {

    public final Boolean value;

    public BooleanLiteral(
            ComplexSymbolFactory.Location location,
            Boolean value
    ) {
        super(location);

        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "BooleanLiteral{" +
                "value=" + value +
                '}';
    }
}
