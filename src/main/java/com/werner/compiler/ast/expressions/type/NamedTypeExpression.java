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
        return "NamedType(" + identifier + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "NamedType(\n"
                + identifier.print(depth + 1) + "\n"
                + result + ")";
    }
}
