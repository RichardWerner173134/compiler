package com.werner.compiler.ast.declaration;

import com.werner.compiler.ast.statements.Statement;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Declaration extends Statement {

    public Declaration(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
