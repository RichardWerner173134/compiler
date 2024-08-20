package com.werner.compiler.exceptions;

import java_cup.runtime.XMLElement;

public class CompilerError extends Exception {

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
}
