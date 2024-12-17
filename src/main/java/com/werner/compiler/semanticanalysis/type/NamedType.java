package com.werner.compiler.semanticanalysis.type;


import com.werner.compiler.semanticanalysis.Identifier;

public class NamedType extends Type {
    public final Identifier identifier;

    public NamedType(Identifier identifier) {
        this.identifier = identifier;
    }
}
