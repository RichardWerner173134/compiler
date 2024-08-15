package com.werner.compiler.ast;

public enum Type {
    STRING, INTEGER, BOOLEAN;

    @Override
    public String toString() {
        return this.name();
    }
}
