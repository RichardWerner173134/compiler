package com.werner.compiler.exceptions;

import java_cup.runtime.ComplexSymbolFactory;

public class CompilerError extends RuntimeException {

    public CompilerError() {
    }

    public CompilerError(String message) {
        super(message);
    }

    public CompilerError(String message, Throwable cause) {
        super(message, cause);
    }

    public CompilerError(Throwable cause) {
        super(cause);
    }

    public CompilerError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static CompilerError RedeclarationOfType(ComplexSymbolFactory.Location location, String name) {
        return new CompilerError("Cant declare Type " + name + ": Duplicate identifier. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError RedeclarationOfVariable(ComplexSymbolFactory.Location location, String name) {
        return new CompilerError("Cant declare variable " + name + ": Duplicate identifier. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError UnknownTypeReference(ComplexSymbolFactory.Location location, String typeName) {
        return new CompilerError("Unknown type " + typeName + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError InvalidDeclarationLocation(ComplexSymbolFactory.Location location, String typeName) {
        return new CompilerError("Cant declare " + typeName + ": Global variables not available. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }
}
