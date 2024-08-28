package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.symboltable.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class RecordTypeExpression extends AbstractTypeExpression {
    public final List<VariableDeclaration> variableDeclarations;

    public RecordTypeExpression(
            ComplexSymbolFactory.Location location,
            List<VariableDeclaration> variableDeclarations
    ) {
        super(location);

        this.variableDeclarations = variableDeclarations;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Record(" + variableDeclarations + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "Record(\n"
                + variableDeclarations
                    .stream()
                    .map(d -> d.print(depth + 1))
                    .collect(Collectors.joining("\n")) + "\n"
                + result + ")";
    }
}
