package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import java_cup.runtime.ComplexSymbolFactory;

public class TypeDeclaration extends Declaration {

    public final Identifier identifier;
    public final AbstractTypeExpression typeExpression;

    public TypeDeclaration(
            ComplexSymbolFactory.Location location,
            Identifier identifier,
            AbstractTypeExpression typeExpression
    ) {
        super(location);

        this.identifier = identifier;
        this.typeExpression = typeExpression;
    }

    @Override
    public String toString() {
        return "TypeDeclaration{" +
                "identifier=" + identifier +
                ", typeExpression=" + typeExpression +
                '}';
    }
}
