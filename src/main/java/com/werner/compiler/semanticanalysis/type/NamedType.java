package com.werner.compiler.semanticanalysis.type;

import com.werner.compiler.semanticanalysis.Symbol;

import javax.naming.Name;

public class NamedType extends Type {
    public final Symbol identifier;

    public NamedType(Symbol identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier.identifier;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NamedType
                && ((NamedType) obj).identifier.equals(this.identifier);
    }
}
