package com.werner.compiler.semanticanalysis.type;

public class ArrayType extends Type {
    public Type baseType;

    public ArrayType(
            Type baseType
    ) {
        this.baseType = baseType;
    }
}
