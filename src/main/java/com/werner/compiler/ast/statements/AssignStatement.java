package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.expressions.Expression;
import java_cup.runtime.ComplexSymbolFactory;

public class AssignStatement extends Statement {
    public final Identifier target;

    public final Expression value;

    public AssignStatement(
            ComplexSymbolFactory.Location location,
            Identifier target,
            Expression value
    ) {
        super(location);
        this.target = target;
        this.value = value;
    }

    @Override
    public String toString() {
        return "AssignStatement{" +
                "target=" + target +
                ", value=" + value +
                '}';
    }
}
