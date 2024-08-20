package com.werner.compiler.symboltable;

import com.werner.compiler.symboltable.type.Type;

public class TypeInfo extends Info {
    public final Type type;

    public TypeInfo(Type type) {
        this.type = type;
    }
}
