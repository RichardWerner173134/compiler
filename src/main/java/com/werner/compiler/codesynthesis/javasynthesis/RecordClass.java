package com.werner.compiler.codesynthesis.javasynthesis;

import com.werner.compiler.semanticanalysis.type.RecordType;

import java.util.List;

public class RecordClass {
    public String generatedClassName;

    public RecordType recordType;

    public RecordClass(
            String generatedClassName,
            RecordType recordType
    ) {
        this.generatedClassName = generatedClassName;
        this.recordType = recordType;
    }
}
