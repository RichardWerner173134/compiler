package com.werner.compiler.ast.expressions;

import java.util.List;

public enum Operator {
    ADD, SUB, MUL, DIV, EQU, NEQ;

    public boolean isArithmetic() {
        return List.of(ADD, SUB, MUL, DIV).contains(this);
    }

    public boolean isComparison(){
        return !this.isArithmetic();
    }
}
