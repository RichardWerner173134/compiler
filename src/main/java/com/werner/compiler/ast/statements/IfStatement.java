package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.expressions.Expression;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IfStatement extends Statement {

    public final Expression condition;
    public final List<Statement> ifStatements;
    public final Optional<List<Statement>> elseStatements;

    public IfStatement(
            ComplexSymbolFactory.Location location,
            Expression condition,
            List<Statement> ifStatements,
            Optional<List<Statement>> elseStatements
    ) {
        super(location);
        this.condition = condition;
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
    }

    @Override
    public String toString() {
        return "IfStatement(" + condition + ", " + ifStatements + ", " + elseStatements + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "IfStatement(\n"
                + condition.print(depth + 1) + "\n"

                + result + "\t" + "[" + "\n"
                + ifStatements
                    .stream()
                    .map(s -> s.print(depth + 2))
                    .collect(Collectors.joining("\n"))
                + "\n"
                + result + "\t" + "]" + "\n"

                + elseStatements.map(statements ->
                        result + "\t" + "[" + "\n" +
                            statements
                                .stream()
                                .map(s -> s.print(depth + 2))
                                .collect(Collectors.joining("\n"))
                        + "\n" + result + "\t" + "]"
                    )
                    .orElse(result + "\t" + "[]")
                + "\n" + result + ")";
    }

}
