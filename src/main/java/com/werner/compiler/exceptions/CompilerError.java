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

    /**
     * @param location
     * @return location incremented by 1, because ComplexSymbolFactory.Location.getLine() is zero indexed
     */
    private static int plusOne(ComplexSymbolFactory.Location location) {
        return location.getLine() + 1;
    }

    public static CompilerError RedeclarationOfType(ComplexSymbolFactory.Location location, String name) {
        return new CompilerError("Cant declare Type " + name + ": Duplicate identifier. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError RedeclarationOfVariable(ComplexSymbolFactory.Location location, String name) {
        return new CompilerError("Cant declare variable " + name + ": Duplicate identifier. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError UnknownTypeReference(ComplexSymbolFactory.Location location, String typeName) {
        return new CompilerError("Unknown type " + typeName + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError InvalidDeclarationLocation(ComplexSymbolFactory.Location location, String declaration) {
        return new CompilerError("Cant declare " + declaration + ": Global variables not available. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError UnknownIdentifier(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Unknown Identifier " + identifier + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError NotAVariable(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Mismatching reference. " + identifier + " is not a variable. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeError(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types. Expecting " + type1 + " but got " + type2 + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError ArrayInitializationError(ComplexSymbolFactory.Location location) {
        return new CompilerError("ArrayInitializationError. Error at line=" + location);
    }

    public static CompilerError NotAType(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Mismatching reference. " + identifier + " is not a type. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorArithmeticExpression(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types for Arithmetic Expression. Expecting " + PrimitiveType.INT_TYPE.typeName + ", but got " +
                type1 + " and " + type2 + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorEqualityComparisonExpression(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types for EqualityComparison. Expecting " + type1 + " to match with " +
                type2 + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorIntegerComparison(ComplexSymbolFactory.Location location, String type1, String type2) {
        return new CompilerError("Mismatching types for IntegerComparison. Expecting " + PrimitiveType.INT_TYPE.typeName + ", but got " + type1
                + " and " + type2 + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorCondition(ComplexSymbolFactory.Location location, String actualType) {
        return new CompilerError("Mismatching types for Condition. Expecting " + PrimitiveType.BOOLEAN_TYPE.typeName + ", but got " + actualType
                + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError NoTypedReturnAllowed(ComplexSymbolFactory.Location location) {
        return new CompilerError("Typed return statement is not allowed in a procedure. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError NoEmptyReturnAllowed(ComplexSymbolFactory.Location location) {
        return new CompilerError("Empty return statement is not allowed in a function. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError NotAFunction(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Mismatching reference. " + identifier + " is not a function. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorFunctionCall(ComplexSymbolFactory.Location location, String parameterType, String argumentType, int argumentIndex) {
        return new CompilerError("Mismatching types for function at index " + argumentIndex + ". Expected " + parameterType + ", but got " + argumentType
                + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError TypeErrorProcedureCall(ComplexSymbolFactory.Location location, String parameterType, String argumentType, int argumentIndex) {
        return new CompilerError("Mismatching types for procedure at index " + argumentIndex + ". Expected " + parameterType + ", but got " + argumentType
                + ". Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError InvalidArgumentCount(ComplexSymbolFactory.Location location, int argumentCount) {
        return new CompilerError("No Matching function with " + argumentCount
                + " arguments found. Error at line=" + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError MissingReturnStatement(ComplexSymbolFactory.Location location, String identifier) {
        return new CompilerError("Missing return statement for function " + identifier + ". Error at line="
                + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError InvalidStatementLocation(ComplexSymbolFactory.Location location, String statement) {
        return new CompilerError(statement + " is not allowed in this location. Error at line="
                + plusOne(location) + ", column=" + location.getColumn());
    }

    public static CompilerError MainProcedureMissing() {
        return new CompilerError("Main procedure is missing");
    }

    public static CompilerError MainProcedureArgumentCountNotZero(String kind) {
        return new CompilerError("Identifier main must be a procedure. Found a " + kind + ".");
    }

    public static CompilerError MainProcedureArgumentCountNotZero(int numberOfArguments) {
        return new CompilerError("Main procedure must have 0 arguments. Found " + numberOfArguments + " arguments.");
    }
}