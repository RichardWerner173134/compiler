package com.werner.compiler.symboltable.type;

public class ArrayType extends Type {
    public Type baseType;

    public ArrayType(
            Type baseType
    ) {
        this.baseType = baseType;
    }
}
