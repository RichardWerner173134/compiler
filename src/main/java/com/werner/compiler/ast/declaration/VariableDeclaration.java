package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * VariableDeclaration has the production "variable_declaration -> name: type"
 *
 * this is not a statement
 *
 * @link com.werner.compiler.ast.statements.VariableDeclarationStatement
 *
 */
public class VariableDeclaration extends Node {

    public final Identifier identifier;

    public final AbstractTypeExpression typeExpression;

    public VariableDeclaration(
            ComplexSymbolFactory.Location location,
            Identifier identifier,
            AbstractTypeExpression typeExpression
    ) {
        super(location);

        this.identifier = identifier;
        this.typeExpression = typeExpression;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "VarDeclaration(" + identifier + ", " + typeExpression + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "VarDeclaration(\n"
                + identifier.print(depth + 1) + "\n"
                + typeExpression.print(depth + 1) + "\n"
                + result + ")";
    }
}
