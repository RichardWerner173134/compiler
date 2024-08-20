package com.werner.compiler.symboltable.type;

public class ArrayType extends Type {
    public final int indexSize;
    public final Type baseType;

    public ArrayType(
            int indexSize,
            Type baseType
    ) {
        this.indexSize = indexSize;
        this.baseType = baseType;
    }
}
