package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.declaration.VariableDeclaration;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;

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
        return "RecordTypeExpression{" +
                "variableDeclarations=" + variableDeclarations +
                '}';
    }
}
