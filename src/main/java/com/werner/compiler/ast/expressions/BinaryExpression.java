package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Type;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class BinaryExpression extends Expression {
    public final Operator operator;
    public final Expression leftOperand;
    public final Expression rightOperand;

    public BinaryExpression(
            ComplexSymbolFactory.Location location,
            Type dataType,
            Operator operator,
            Expression leftOperand,
            Expression rightOperand
    ) {
        super(location, dataType);

        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "operator=" + operator +
                ", leftOperand=" + leftOperand +
                ", rightOperand=" + rightOperand +
                ", dataType=" + dataType +
                '}';
    }
}
