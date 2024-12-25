package com.werner.compiler.exceptions;

import com.werner.compiler.semanticanalysis.type.PrimitiveType;
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

    public static CompilerError InvalidDeclarationLocation(ComplexSymbolFactory.Location location, String declaration) {
        return new CompilerError("Cant declare " + declaration + ": Global variables not available. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError UnknownIdentifier(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Unknown Identifier " + identifier + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError NotAVariable(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Mismatching reference. " + identifier + " is not a variable. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError TypeError(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types. Expecting " + type1 + " but got " + type2 + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError ArrayInitializationError(ComplexSymbolFactory.Location location) {
        return new CompilerError("ArrayInitializationError. Error at line=" + location);
    }

    public static CompilerError NotAType(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Mismatching reference. " + identifier + " is not a type. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorArithmeticExpression(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types for Arithmetic Expression. Expecting " + PrimitiveType.INT_TYPE.typeName + ", but got " +
                type1 + " and " + type2 + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorEqualityComparisonExpression(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types for EqualityComparison. Expecting " + type1 + " to match with " +
                type2 + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorIntegerComparison(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types for IntegerComparison. Expecting " + PrimitiveType.INT_TYPE.typeName + ", but got " + type1
                + " and " + type2 + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorCondition(ComplexSymbolFactory.Location location, String actualType) {
        return new CompilerError("Mismatching types for Condition. Expecting " + PrimitiveType.BOOLEAN_TYPE.typeName + ", but got " + actualType
                + ". Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }

    public static CompilerError NoReturnAllowed(ComplexSymbolFactory.Location location) {
        return new CompilerError("Return statement is here not allowed. Error at line=" + location.getLine() + ", column=" + location.getColumn());
    }
}
