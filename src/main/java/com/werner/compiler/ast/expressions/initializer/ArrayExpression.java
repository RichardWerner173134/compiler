package com.werner.compiler.ast.expressions.initializer;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayExpression extends AbstractObjectInitialization {

    public final List<Expression> indexSizes;

    public final AbstractTypeExpression typeExpression;

    public ArrayExpression(
            ComplexSymbolFactory.Location location,
            List<Expression> indexSizes,
            AbstractTypeExpression typeExpression
    ) {
        super(location);

        this.indexSizes = indexSizes;
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

                + result + "\t" + "IndexSizes(" + "\n"
                + indexSizes.stream().map(index -> index.print(depth + 2)).collect(Collectors.joining()) + "\n"
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
