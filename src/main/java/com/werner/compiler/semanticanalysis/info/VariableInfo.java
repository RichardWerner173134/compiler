package com.werner.compiler.semanticanalysis.info;

import com.werner.compiler.semanticanalysis.Kind;

public class VariableInfo extends Info {

    public final boolean isReferenceParameter;

    public VariableInfo(
            boolean isReferenceParameter
    ) {
        this.isReferenceParameter = isReferenceParameter;
    }

    @Override
    public Kind getKind() {
        return Kind.VARIABLE;
    }
}
