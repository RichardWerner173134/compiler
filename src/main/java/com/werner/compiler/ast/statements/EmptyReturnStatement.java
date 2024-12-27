package com.werner.compiler.ast.statements;

import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class EmptyReturnStatement extends Statement {
    public EmptyReturnStatement(ComplexSymbolFactory.Location location) {
        super(location);
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "EmptyReturn";
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
