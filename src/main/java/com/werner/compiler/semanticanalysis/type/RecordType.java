package com.werner.compiler.semanticanalysis.type;

import java.util.ArrayList;
import java.util.List;

public class RecordType extends Type {
    public List<Type> recordTypes = new ArrayList<>();

    public RecordType(List<Type> recordTypes) {
        this.recordTypes = recordTypes;
    }
}
