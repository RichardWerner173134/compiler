package com.werner.compiler.semanticanalysis;

public class Symbol {
    public final String identifier;

    public Symbol(String identifier) {
        this.identifier = identifier.intern();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Symbol
                && ((Symbol) o).identifier.equals(identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return identifier;
    }
}
