package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class NamedTypeExpression extends AbstractTypeExpression {

    public final Identifier identifier;

    public NamedTypeExpression(
            ComplexSymbolFactory.Location location,
            Identifier identifier
    ) {
        super(location);

        this.identifier = identifier;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "NamedTypeExpression{" +
                "identifier=" + identifier +
                '}';
    }
}
