package com.werner.compiler.ast;

import com.werner.compiler.ast.printer.Printable;
import com.werner.compiler.symboltable.visitor.Visitable;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class Node implements Visitable, Printable {

    public final ComplexSymbolFactory.Location location;

    public Node(ComplexSymbolFactory.Location location) {
        this.location = location;
    }
}
