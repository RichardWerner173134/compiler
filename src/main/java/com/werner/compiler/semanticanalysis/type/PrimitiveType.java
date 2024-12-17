package com.werner.compiler.semanticanalysis.type;

public class PrimitiveType extends Type {
    public final String typeName;

    public PrimitiveType(String typeName) {
        this.typeName = typeName;
    }

    public final static Type INT_TYPE = new PrimitiveType("int");
    public final static Type BOOLEAN_TYPE = new PrimitiveType("bool");
    public final static Type STRING_TYPE = new PrimitiveType("string");
}
