package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class VariableDeclaration extends Declaration {

    public final Identifier identifier;

    public VariableDeclaration(
            ComplexSymbolFactory.Location location,
            Identifier identifier
    ) {
        super(location);
        this.identifier = identifier;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
