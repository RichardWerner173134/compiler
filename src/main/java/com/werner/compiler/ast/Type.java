package com.werner.compiler.ast;

public enum Type {
    STRING, INTEGER;

    @Override
    public String toString() {
        return this.name();
    }
}
