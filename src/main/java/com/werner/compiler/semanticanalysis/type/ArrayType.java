package com.werner.compiler.semanticanalysis.type;

public class ArrayType extends Type {
    public Type baseType;

    public ArrayType(
            Type baseType
    ) {
        this.baseType = baseType;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ArrayType
                && ((ArrayType) other).baseType.equals(this.baseType);
    }

    @Override
    public String toString() {
        return "Arr(" + baseType.toString() + ")";
    }
}
