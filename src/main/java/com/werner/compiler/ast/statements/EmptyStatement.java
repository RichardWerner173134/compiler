package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.visitor.Visitor;
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
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "EmptyStatement";
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
