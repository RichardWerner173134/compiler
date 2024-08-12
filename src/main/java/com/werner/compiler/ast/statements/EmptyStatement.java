package com.werner.compiler.ast.statements;

import java_cup.runtime.ComplexSymbolFactory;

public class EmptyStatement extends Statement {

    public EmptyStatement(ComplexSymbolFactory.Location location) {
        super(location);
    }
}
