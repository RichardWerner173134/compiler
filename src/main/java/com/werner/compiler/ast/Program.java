package com.werner.compiler.ast;

import com.werner.compiler.ast.statements.Statement;
import com.werner.compiler.ast.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.List;

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
        return "Program{" +
                "statementList=" + statementList +
                '}';
    }
}
