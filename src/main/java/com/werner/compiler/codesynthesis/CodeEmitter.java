package com.werner.compiler.codesynthesis;

public class CodeEmitter {
    private StringBuilder sb;

    public CodeEmitter() {
        this.sb = new StringBuilder();
    }

    public void emit(String s) {
        sb.append(s + "\n");
    }

    public void print() {
        System.out.println("\nCompiled assembly...\n\n" + sb.toString());
    }
}
