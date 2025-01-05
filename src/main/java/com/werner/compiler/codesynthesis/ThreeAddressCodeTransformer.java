package com.werner.compiler.codesynthesis;

import com.werner.compiler.ast.expressions.BinaryExpression;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.expressions.FunctionCall;
import com.werner.compiler.ast.expressions.VariableExpression;
import com.werner.compiler.ast.expressions.literals.BooleanLiteral;
import com.werner.compiler.ast.expressions.literals.IntLiteral;

import java.util.ArrayList;
import java.util.List;

public class ThreeAddressCodeTransformer {
    private static final String ZERO = "0";

    private NameProvider nameProvider;

    public ThreeAddressCodeTransformer(
            NameProvider nameProvider
    ) {
        this.nameProvider = nameProvider;
    }

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

            String nextTempVariable = nameProvider.getNextTempVariable();
            ThreeAddressCode tac = new ThreeAddressCode(nextTempVariable, tacOperator, leftTacs.getLast().getTarget(), rightTacs.getLast().getTarget());
            result.add(tac);
        }

        if (expression instanceof IntLiteral) {
            String nextTempVariable = nameProvider.getNextTempVariable();
            ThreeAddressCode threeAddressCode = new ThreeAddressCode(nextTempVariable, Operator.ADD, ((IntLiteral) expression).value.toString(), ZERO);
            result.add(threeAddressCode);
        }

        if (expression instanceof BooleanLiteral) {
            String nextTempVariable = nameProvider.getNextTempVariable();
            ThreeAddressCode threeAddressCode = new ThreeAddressCode(nextTempVariable, Operator.ADD,
                    ((BooleanLiteral) expression).value
                            ? String.valueOf(1)     /*TRUE*/
                            : String.valueOf(0),    /*FALSE*/
                    ZERO);
            result.add(threeAddressCode);
        }

        if (expression instanceof VariableExpression) {
            String nextTempVariable = nameProvider.getNextTempVariable();
            ThreeAddressCode threeAddressCode = new ThreeAddressCode(nextTempVariable, Operator.ADD, ((VariableExpression) expression).identifier.name, ZERO);
            result.add(threeAddressCode);
        }

        if (expression instanceof FunctionCall) {
            // TODO implement ActivationRecord
        }

        return result;
    }
}
