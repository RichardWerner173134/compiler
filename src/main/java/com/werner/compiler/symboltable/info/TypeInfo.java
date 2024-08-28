package com.werner.compiler.symboltable.info;

import com.werner.compiler.symboltable.Kind;
import com.werner.compiler.symboltable.type.Type;

public class TypeInfo extends Info {
    public final Type type;
    public final Kind kind;

    public TypeInfo(
            Type type,
            Kind kind
    ) {
        this.type = type;
        this.kind = kind;
    }

    @Override
    public Kind getKind() {
        return kind;
    }
}
