package com.werner.compiler.ast.expressions.literals;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class BooleanLiteral extends Expression {

    public final Boolean value;

    public BooleanLiteral(
            ComplexSymbolFactory.Location location,
            Boolean value
    ) {
        super(location);

        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Bool(" + value + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "Bool(" + value + ")";
    }
}
