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
        boolean isRecordType = obj instanceof RecordType;

        if(isRecordType == false || ((RecordType) obj).recordTypes.size() != recordTypes.size()) {
            return false;
        }

        for (int i = 0; i < ((RecordType) obj).recordTypes.size(); i++) {
            boolean typesMatch = recordTypes.get(i).equals(((RecordType) obj).recordTypes.get(i));
            if (typesMatch == false) {
                return false;
            }
        }

        return true;
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
