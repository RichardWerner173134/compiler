package com.werner.compiler.ast.expressions.type;

import com.werner.compiler.ast.expressions.Expression;
import com.werner.compiler.semanticanalysis.type.Type;
import java_cup.runtime.ComplexSymbolFactory;

public abstract class AbstractTypeExpression extends Expression {

    public Type symbolType;

    public AbstractTypeExpression(ComplexSymbolFactory.Location location) {
        super(location);
    }

    public Type getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(Type symbolType) {
        this.symbolType = symbolType;
    }
}
