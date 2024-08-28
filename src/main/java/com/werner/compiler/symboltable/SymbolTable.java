package com.werner.compiler.symboltable;

import com.werner.compiler.exceptions.CompilerError;
import com.werner.compiler.symboltable.info.Info;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SymbolTable {
    public final Map<Identifier, Info> entries = new HashMap<>();
    public final Optional<SymbolTable> outerScope;

    public SymbolTable() {
        this.outerScope = Optional.empty();
    }

    public Info lookup(Identifier name) {
        if (entries.containsKey(name)){
            return entries.get(name);
        }

        return outerScope.map(symbolTable -> symbolTable.lookup(name)).orElse(null);
    }

    public void enter(Identifier name, Info info) {
        entries.putIfAbsent(name, info);
    }

    public void enter(Identifier name, Info info, CompilerError error) {
        if (entries.containsKey(name)){
            throw error;
        } else {
            entries.put(name, info);
        }
    }
}
