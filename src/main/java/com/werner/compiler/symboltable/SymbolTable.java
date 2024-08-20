package com.werner.compiler.symboltable;

import com.werner.compiler.exceptions.CompilerError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SymbolTable {
    private final Map<Identifier, Info> entries = new HashMap<>();
    private final Optional<SymbolTable> outerScope;

    public SymbolTable() {
        this.outerScope = Optional.empty();
    }

    public Info lookup(Identifier name) {
        if (entries.containsKey(name)){
            return entries.get(name);
        } else if (outerScope.isPresent()){
            return outerScope.get().lookup(name);
        } else {
            return null;
        }
    }

    public void enter(Identifier name, Info info) {
        entries.putIfAbsent(name, info);
    }

    public void enter(Identifier name, Info info, CompilerError error) throws CompilerError {
        if (entries.containsKey(name)){
            throw error;
        } else {
            entries.put(name, info);
        }
    }
}
