package com.werner.compiler.codesynthesis;

public enum Operator {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),

    EQ("=="),
    NEQ("!="),
    LT("<"),
    LET("<="),
    GT(">"),
    GET(">=")
    ;

    public final String value;

    private Operator(String value) {
        this.value = value;
    }

    public static Operator fromAstOperator(com.werner.compiler.ast.expressions.Operator operator) {
        return switch (operator){
            case ADD -> ADD;
            case SUB -> SUB;
            case MUL -> MUL;
            case DIV -> DIV;

            case EQ -> EQ;
            case NEQ -> NEQ;
            case LESS -> LT;
            case LESSOREQUAL -> LET;
            case MORE -> GT;
            case MOREOREQUAL -> GET;
        };
    }
}
