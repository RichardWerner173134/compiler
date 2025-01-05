package com.werner.compiler.semanticanalysis.info;

import com.werner.compiler.semanticanalysis.Kind;
import com.werner.compiler.semanticanalysis.type.Type;

public class VariableInfo extends Info {

    public final boolean isReferenceParameter;
    public final Type type;

    public VariableInfo(
            boolean isReferenceParameter,
            Type type
    ) {
        this.isReferenceParameter = isReferenceParameter;
        this.type = type;
    }

    @Override
    public Kind getKind() {
        return Kind.VARIABLE;
    }
}
