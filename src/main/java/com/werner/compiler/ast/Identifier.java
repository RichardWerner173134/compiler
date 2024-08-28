package com.werner.compiler.ast;

import com.werner.compiler.symboltable.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public class Identifier extends Node {

    public final String name;

    public Identifier(
            ComplexSymbolFactory.Location location,
            String name
    ) {
        super(location);

        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return "Ident(" + name + ")";
    }

    @Override
    public String print(int depth) {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, depth)));

        return result + "Ident(" + name + ")";
    }

}
