package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableDeclarationStatement extends Statement {

    public final VariableDeclaration variableDeclaration;

    public VariableDeclarationStatement(
            ComplexSymbolFactory.Location location,
            VariableDeclaration variableDeclaration
    ) {
        super(location);

        this.variableDeclaration = variableDeclaration;
    }

    @Override
    public String toString() {
        return "VarDeclarationStatement(" + variableDeclaration + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "VarDeclarationStatement(\n"
                + variableDeclaration.print(depth + 1) + "\n"
                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
