package com.werner.compiler.semanticanalysis.type;

public class PrimitiveType extends Type {
    public final String typeName;

    public PrimitiveType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PrimitiveType
                && ((PrimitiveType)obj).typeName.equals(this.typeName);
    }

    public final static PrimitiveType INT_TYPE = new PrimitiveType(com.werner.compiler.ast.PrimitiveType.INTEGER.toString());
    public final static PrimitiveType BOOLEAN_TYPE = new PrimitiveType(com.werner.compiler.ast.PrimitiveType.BOOLEAN.toString());
    public final static PrimitiveType STRING_TYPE = new PrimitiveType(com.werner.compiler.ast.PrimitiveType.STRING.toString());
}
