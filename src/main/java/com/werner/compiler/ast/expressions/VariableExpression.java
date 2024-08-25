package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableExpression extends Expression {
    public final Identifier identifier;

    public VariableExpression(
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
        return identifier.toString();
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "IDENT(" + identifier.print(depth + 1) + ")";
    }

}
