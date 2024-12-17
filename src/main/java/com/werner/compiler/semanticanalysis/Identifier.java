package com.werner.compiler.semanticanalysis;

public class Identifier {
    public final String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier.intern();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Identifier
                && ((Identifier) o).identifier.equals(identifier);
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
