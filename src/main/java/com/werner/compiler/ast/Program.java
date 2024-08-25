package com.werner.compiler.ast;

import com.werner.compiler.ast.statements.Statement;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Program extends Node {

    public List<Statement> statementList;

    public Program(
            ComplexSymbolFactory.Location location,
            List<Statement> statementList
    ) {
        super(location);

        this.statementList = statementList;
    }


    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Program(" + statementList + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "Program[\n"
                + statementList
                    .stream()
                    .map(s -> s.print(depth + 1))
                    .collect(Collectors.joining("\n"))
                + "\n]";
    }

}
