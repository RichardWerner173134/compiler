package com.werner.compiler.symboltable.type;

import java.util.ArrayList;
import java.util.List;

public class RecordType extends Type {
    public List<Type> recordTypes = new ArrayList<>();

    public RecordType(List<Type> recordTypes) {
        this.recordTypes = recordTypes;
    }
}
