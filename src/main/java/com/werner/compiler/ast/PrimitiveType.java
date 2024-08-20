package com.werner.compiler.ast;

public enum PrimitiveType {
    STRING, INTEGER, BOOLEAN;

    @Override
    public String toString() {
        return this.name();
    }
}
