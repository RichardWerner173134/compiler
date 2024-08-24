package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.PrimitiveType;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class PrimitiveTypeExpression extends AbstractTypeExpression {

    public final PrimitiveType primitiveType;

    public PrimitiveTypeExpression(
            ComplexSymbolFactory.Location location,
            PrimitiveType primitiveType
    ) {
        super(location);

        this.primitiveType = primitiveType;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "PrimitiveTypeExpression{" +
                "primitiveType=" + primitiveType +
                '}';
    }
}
