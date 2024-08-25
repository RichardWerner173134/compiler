package com.werner.compiler.ast.statements;

import java_cup.runtime.ComplexSymbolFactory;

public class EmptyStatement extends Statement {

    public EmptyStatement(ComplexSymbolFactory.Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "EmptyStatement";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "EmptyStatement";
    }

}
