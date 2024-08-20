package com.werner.compiler.ast.visitor;

import com.werner.compiler.symboltable.SymbolTable;

public class NameAnalysisVisitor extends EmptyVisitor {
    private final SymbolTable symbolTable;

    public NameAnalysisVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
