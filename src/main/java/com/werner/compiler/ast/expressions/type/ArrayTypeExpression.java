package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;

public class ArrayTypeExpression extends AbstractTypeExpression {

    public final int indexSize;

    public final AbstractTypeExpression typeExpression;

    public ArrayTypeExpression(
            ComplexSymbolFactory.Location location,
            int indexSize,
            AbstractTypeExpression typeExpression
    ) {
        super(location);

        this.indexSize = indexSize;
        this.typeExpression = typeExpression;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Arr(" + typeExpression + "[" + indexSize + "]" + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "Arr(\n"
                + typeExpression.print(depth + 1) + "[" + indexSize + "]" + "\n"
                + result + ")";
    }
}
