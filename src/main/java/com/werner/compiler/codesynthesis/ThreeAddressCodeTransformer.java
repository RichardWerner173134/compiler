package com.werner.compiler.codesynthesis;

import com.werner.compiler.ast.expressions.BinaryExpression;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.expressions.literals.IntLiteral;

import java.util.ArrayList;
import java.util.List;

public class ThreeAddressCodeTransformer {
    private static final char TEMP_PREFIX = 't';
    private static final String ZERO = "0";
    private int tempCounter = 0;

    public List<ThreeAddressCode> transform(Expression expression) {
        System.out.println("Transforming " + expression.getClass().getSimpleName());

        List<ThreeAddressCode> result = new ArrayList<>();

        if (expression instanceof BinaryExpression) {
            com.werner.compiler.ast.expressions.Operator astOperator = ((BinaryExpression) expression).operator;
            Operator tacOperator = Operator.fromAstOperator(astOperator);

            Expression astLeft = ((BinaryExpression) expression).leftOperand;
            List<ThreeAddressCode> leftTacs = transform(astLeft);
            result.addAll(leftTacs);

            Expression astRight = ((BinaryExpression) expression).rightOperand;
            List<ThreeAddressCode> rightTacs = transform(astRight);
            result.addAll(rightTacs);

            ThreeAddressCode tac = new ThreeAddressCode(getNextTempVariable(), tacOperator, leftTacs.getLast().getTarget(), rightTacs.getLast().getTarget());
            result.add(tac);
        }

        if (expression instanceof IntLiteral) {
            ThreeAddressCode threeAddressCode = new ThreeAddressCode(getNextTempVariable(), Operator.ADD, ((IntLiteral) expression).value.toString(), ZERO);
            result.add(threeAddressCode);
        }

        return result;
    }

    private String getNextTempVariable() {
        return "" + TEMP_PREFIX + tempCounter++;
    }
}
