package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.expressions.type.AbstractTypeExpression;
import com.werner.compiler.symboltable.visitor.Visitor;
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
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "TypeDeclaration(\n"
                + identifier.print(depth + 1) + "\n"
                + typeExpression.print(depth + 1) + "\n"
                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
