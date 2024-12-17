package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
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
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "VariableExpression(\n"
                + identifier.print(depth + 1) + "\n"
                + result + ")";
    }

}
