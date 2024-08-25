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
        return "TypeDeclaration(" + identifier + ", " + typeExpression + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "TypeDeclaration(\n"
                + identifier.print(depth + 1) + "\n"
                + typeExpression.print(depth + 1) + "\n"
                + result + ")";
    }
}
