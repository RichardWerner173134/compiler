package com.werner.compiler.ast;

import com.werner.compiler.ast.visitor.Visitable;
import java_cup.runtime.*;

public abstract class Node implements Visitable {

    public final ComplexSymbolFactory.Location location;

    public Node(ComplexSymbolFactory.Location location) {
        this.location = location;
    }

}
