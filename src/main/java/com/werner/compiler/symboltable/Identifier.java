package com.werner.compiler.symboltable;

import java.util.Objects;

public class Identifier {
    public final String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier.intern();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Identifier)
                && ((Identifier) o).identifier.equals(identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
