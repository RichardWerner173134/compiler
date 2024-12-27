package com.werner.compiler.ast.expressions;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall extends Expression {

    public final Identifier identifier;
    public final List<Expression> argumentList;

    public FunctionCall(
            ComplexSymbolFactory.Location location,
            Identifier identifier,
            List<Expression> argumentList
    ) {
        super(location);

        this.identifier = identifier;
        this.argumentList = argumentList;
    }

    @Override
    public String toString() {
        return "FunctionCall(" + identifier + ", " + argumentList + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "FunctionCall(\n"
                + identifier.print(depth + 1) + "\n"
                + argumentList.stream().map(a -> a.print(depth + 1)).collect(Collectors.joining("\n")) + "\n"
                + result + ")";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
