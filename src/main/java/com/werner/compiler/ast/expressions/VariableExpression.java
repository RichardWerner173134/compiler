package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.PrimitiveType;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableExpression extends Expression {
    public final Identifier identifier;

    public VariableExpression(
            ComplexSymbolFactory.Location location,
            Identifier identifier,
            PrimitiveType dataType
    ) {
        super(location, dataType);

        this.identifier = identifier;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "VariableExpression{" +
                "identifier=" + identifier +
                '}';
    }
}
