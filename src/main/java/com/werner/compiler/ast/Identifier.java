package com.werner.compiler.ast;

import com.werner.compiler.ast.visitor.Visitor;
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
        return "Identifier{" +
                "name='" + name + '\'' +
                '}';
    }
}
