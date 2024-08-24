package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableDeclaration extends Declaration {

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
        return "VariableDeclaration{" +
                "identifier=" + identifier +
                ", typeExpression=" + typeExpression +
                '}';
    }
}
