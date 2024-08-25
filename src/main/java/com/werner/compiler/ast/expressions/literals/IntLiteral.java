package com.werner.compiler.ast.expressions.literals;

import com.werner.compiler.ast.Node;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Collections;
import java.util.List;

public class IntLiteral extends Expression {
    public final Integer value;

    public IntLiteral(
            ComplexSymbolFactory.Location location,
            Integer value
    ) {
        super(location);

        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Int(" + value + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("\t");
        }

        return result + "Int(" + value + ")";
    }
}
