package com.werner.compiler.ast.expressions.literals;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class StringLiteral extends Expression {
    public final String value;

    public StringLiteral(
            ComplexSymbolFactory.Location location,
            String value
    ) {
        super(location);

        this.value = value;
    }

    @Override
    public String toString() {
        return "String(" + value + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "String(" + value + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
