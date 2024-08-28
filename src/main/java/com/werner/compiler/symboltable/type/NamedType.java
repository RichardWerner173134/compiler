package com.werner.compiler.symboltable.type;


import com.werner.compiler.symboltable.Identifier;

public class NamedType extends Type {
    public final Identifier identifier;

    public NamedType(Identifier identifier) {
        this.identifier = identifier;
    }
}
