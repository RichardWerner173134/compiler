package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

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
        return "ArrayTypeExpression{" +
                "indexSize=" + indexSize +
                ", typeExpression=" + typeExpression +
                '}';
    }
}
