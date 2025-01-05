package com.werner.compiler.codesynthesis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeEmitter {
    private StringBuilder sb;

    public CodeEmitter() {
        this.sb = new StringBuilder();
    }

    public void emit(String s) {
        sb.append(s).append("\n");
    }

    public void print() {
        System.out.println("\nCompiled assembly...\n\n" + sb.toString());

        File yourFile = new File("C:\\3_Code\\java\\jflex-cup\\input\\Main.java");
        BufferedWriter writer = null;
        try {
            yourFile.createNewFile();

            writer = new BufferedWriter(new FileWriter(yourFile));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
