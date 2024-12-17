package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class ArrayTypeExpression extends AbstractTypeExpression {

    public final AbstractTypeExpression typeExpression;

    public ArrayTypeExpression(
            ComplexSymbolFactory.Location location,
            AbstractTypeExpression typeExpression
    ) {
        super(location);

        this.typeExpression = typeExpression;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Arr(" + typeExpression + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "Arr(\n"
                + typeExpression.print(depth + 1) + "\n"
                + result + ")";
    }
}
