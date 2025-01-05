package com.werner.compiler.codesynthesis;

public class NameProvider {
    private static final char TEMP_PREFIX = 't';
    private static final String LABEL_PREFIX = "LABEL_";
    private int tempVariableCounter = 0;
    private int labelCounter = 0;

    public String getNextTempVariable() {
        return String.valueOf(TEMP_PREFIX) + tempVariableCounter++;
    }

    public String getNextLabel() {
        return LABEL_PREFIX + labelCounter++;
    }
}
