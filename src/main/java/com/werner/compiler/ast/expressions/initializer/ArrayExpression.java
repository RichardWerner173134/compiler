package com.werner.compiler.ast.expressions.initializer;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class ArrayExpression extends AbstractObjectInitialization {

    public final Expression indexSize;

    public final AbstractTypeExpression typeExpression;

    public ArrayExpression(
            ComplexSymbolFactory.Location location,
            Expression indexSize,
            AbstractTypeExpression typeExpression
    ) {
        super(location);

        this.indexSize = indexSize;
        this.typeExpression = typeExpression;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "NewArr(\n"

                + result + "\t" + "IndexSize(" + "\n"
                + indexSize.print(depth + 2) + "\n"
                + result + "\t" + ")" + "\n"

                + result + "\t" + "ElementType(" + "\n"
                + typeExpression.print(depth + 2) + "\n"
                + result + "\t" + ")" + "\n"

                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
