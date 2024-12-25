package com.werner.compiler.semanticanalysis.info;

import com.werner.compiler.semanticanalysis.Kind;

import java.util.List;

public class ProcedureInfo extends Info {

    public final List<VariableInfo> parameters;

    public ProcedureInfo(
            List<VariableInfo> parameters
    ) {
        this.parameters = parameters;
    }

    @Override
    public Kind getKind() {
        return Kind.PROCEDURE;
    }
}
