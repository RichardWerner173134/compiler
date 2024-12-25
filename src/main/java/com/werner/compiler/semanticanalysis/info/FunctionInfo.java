package com.werner.compiler.semanticanalysis.info;

import com.werner.compiler.semanticanalysis.Kind;
import com.werner.compiler.semanticanalysis.type.Type;

import java.util.List;

public class FunctionInfo extends Info {

    public final Type type;
    public final List<VariableInfo> parameters;

    public FunctionInfo(
            Type type,
            List<VariableInfo> parameters
    ) {
        this.type = type;
        this.parameters = parameters;
    }

    @Override
    public Kind getKind() {
        return Kind.FUNCTION;
    }
}
