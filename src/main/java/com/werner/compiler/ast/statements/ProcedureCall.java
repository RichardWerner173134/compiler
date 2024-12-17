package com.werner.compiler.ast.statements;

import com.werner.compiler.ast.Identifier;
import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.semanticanalysis.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ProcedureCall extends Statement {

    public final Identifier identifier;
    public final List<Expression> argumentList;
    public ProcedureCall(
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
        return "ProcedureCall(" + identifier + ", " +  argumentList + ')';
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "ProcedureCall(\n"
                + identifier.print(depth + 1) + "\n"
                + result + "\t" + "Params[\n"
                + argumentList.stream().map(a -> a.print(depth + 2)).collect(Collectors.joining("\n")) + "\n"
                + result + "\t" + "]\n"
                + result + ")";
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
