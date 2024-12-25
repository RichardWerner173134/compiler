package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class ReturnStatement extends Statement {

    public final Expression expression;

    public ReturnStatement(
            ComplexSymbolFactory.Location location,
            Expression expression
    ) {
        super(location);

        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ReturnStatement(" + expression + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "Return(\n"
                + expression.print(depth + 1) + "\n"
                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
