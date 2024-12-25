package com.werner.compiler.semanticanalysis.type;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordType extends Type {
    public List<Type> recordTypes = new ArrayList<>();

    public RecordType(List<Type> recordTypes) {
        this.recordTypes = recordTypes;
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String toString() {
        return "Rec(" + recordTypes
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(","))
                + ")";
    }
}
