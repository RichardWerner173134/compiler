package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class BinaryExpression extends Expression {
    public final Operator operator;
    public final Expression leftOperand;
    public final Expression rightOperand;

    public BinaryExpression(
            ComplexSymbolFactory.Location location,
            Operator operator,
            Expression leftOperand,
            Expression rightOperand
    ) {
        super(location);

        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "BinaryExp(" + operator + ", " + leftOperand + ", " + rightOperand + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "BinaryExp(\n"
                + result + "\t" + operator + "\n"
                + leftOperand.print(depth + 1) + "\n"
                + rightOperand.print(depth + 1) + "\n"
                + result + ")";
    }
}
