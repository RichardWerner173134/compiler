package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class IntLiteral extends Expression {
    public final Integer value;

    public IntLiteral(
            ComplexSymbolFactory.Location location,
            Integer value
    ) {
        super(location);

        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "IntLiteral{" +
                "value=" + value +
                '}';
    }
}
