package com.werner.compiler.ast.expressions.initializer;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.symboltable.visitor.Visitor;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class AbstractObjectInitialization extends Expression {

    public AbstractObjectInitialization(
            ComplexSymbolFactory.Location location
    ) {
        super(location);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String print(int depth) {
        return null;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
