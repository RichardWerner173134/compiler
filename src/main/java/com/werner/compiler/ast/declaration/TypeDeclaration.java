package com.werner.compiler.ast.declaration;

import com.werner.compiler.symboltable.Identifier;
import java_cup.runtime.ComplexSymbolFactory;

public class TypeDeclaration extends Declaration {

    public final Identifier identifier;
    public final TypeExpression typeExpression;

    public TypeDeclaration(ComplexSymbolFactory.Location location) {
        super(location);
    }


}
