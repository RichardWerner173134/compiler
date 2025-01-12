package com.werner.compiler.codesynthesis.javasynthesis;

import com.werner.compiler.semanticanalysis.type.RecordType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordClassContainer {

    private int classCounter;
    public List<RecordClass> recordClasses;

    public RecordClassContainer() {
        classCounter = 0;
        this.recordClasses = new ArrayList();
    }

    public Optional<RecordClass> get(RecordType recordType) {
        for (RecordClass recordClass : recordClasses) {
            if (recordClass.recordType.equals(recordType)) {
                return Optional.of(recordClass);
            }
        }

        return Optional.empty();
    }

    public RecordClass add(RecordType recordType, Optional<String> namedClassName) {
        Optional<RecordClass> recordClass = get(recordType);

        if (recordClass.isEmpty()) {
            String className = namedClassName
                    .map(this::capitalizeString)
                    .orElseGet(() -> capitalizeString(getNewClassName()));

            RecordClass result = new RecordClass(className, recordType);
            recordClasses.add(result);
            return result;
        }

        return recordClass.get();
    }

    private String getNewClassName() {
        int suffix = ++classCounter;
        return "class__" + suffix;
    }

    private String capitalizeString(String identifier) {
        return identifier.substring(0, 1).toUpperCase() + identifier.substring(1);
    }
}
