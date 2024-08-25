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
        return "ASGN(" + target + ", " + value + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "ASGN(\n" + target.print(depth + 1) + "\n" + result + "\t" + value + "\n" + result + ")";
    }

}
