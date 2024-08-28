package com.werner.compiler.symboltable.type;

public class ArrayType extends Type {
    public final int indexSize;
    public Type baseType;

    public ArrayType(
            int indexSize,
            Type baseType
    ) {
        this.indexSize = indexSize;
        this.baseType = baseType;
    }
}
