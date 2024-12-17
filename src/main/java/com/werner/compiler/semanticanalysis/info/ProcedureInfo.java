package com.werner.compiler.semanticanalysis.info;

import com.werner.compiler.semanticanalysis.Kind;

public class ProcedureInfo extends Info {

    @Override
    public Kind getKind() {
        return Kind.PROCEDURE;
    }
}
