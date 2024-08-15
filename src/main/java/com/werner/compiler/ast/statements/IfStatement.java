package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.expressions.Expression;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.Optional;

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
        return "IfStatement{" +
                "condition=" + condition +
                ", ifStatements=" + ifStatements +
                ", elseStatements=" + elseStatements +
                '}';
    }
}
