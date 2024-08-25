package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class WhileStatement extends Statement {

    public final Expression condition;

    public final List<Statement> statementList;

    public WhileStatement(
            ComplexSymbolFactory.Location location,
            Expression condition,
            List<Statement> statementList
    ) {
        super(location);

        this.condition = condition;
        this.statementList = statementList;
    }

    @Override
    public String toString() {
        return "WhileStatement(" + condition + ", " + statementList + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "WhileStatement(\n"
                + result + "\t" + "Condition(\n"
                + condition.print(depth + 2) + "\n"
                + result + "\t" + ")\n"

                + result + "\t" + "Statements[" + "\n"
                + statementList.stream().map(s -> s.print(depth + 2)).collect(Collectors.joining("\n")) + "\n"
                + result + "\t" + "]" + "\n"

                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
