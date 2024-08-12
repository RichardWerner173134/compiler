package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Type;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableExpression extends Expression {
    public final Object value;

    public VariableExpression(
            ComplexSymbolFactory.Location location,
            Type dataType,
            Object value
    ) {
        super(location, dataType);

        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "VariableExpression{" +
                "value=" + value +
                ", dataType=" + dataType +
                '}';
    }
}
