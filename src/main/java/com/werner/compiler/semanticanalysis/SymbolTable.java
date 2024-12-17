package com.werner.compiler.semanticanalysis;

import com.werner.compiler.exceptions.CompilerError;
import com.werner.compiler.semanticanalysis.info.Info;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SymbolTable {
    public final Map<Identifier, Info> entries = new HashMap<>();
    public final Optional<SymbolTable> outerScope;

    public SymbolTable() {
        this.outerScope = Optional.empty();
    }

    public SymbolTable(SymbolTable innerSymbolTable) {
        this.outerScope = Optional.of(innerSymbolTable);
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
        if (entries.containsKey(name) || outerScope.map(outer -> outer.entries.containsKey(name)).orElse(false)) {
            throw error;
        } else {
            entries.put(name, info);
        }
    }
}
