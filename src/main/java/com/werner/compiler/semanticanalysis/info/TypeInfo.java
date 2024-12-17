package com.werner.compiler.semanticanalysis.info;

import com.werner.compiler.semanticanalysis.Kind;
import com.werner.compiler.semanticanalysis.type.Type;

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
