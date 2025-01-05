package com.werner.compiler.codesynthesis;

public class ThreeAddressCode {
    private final static int PADDING_SIZE = 6;
    private String target;
    private Operator operator;
    private String left;
    private String right;

    public ThreeAddressCode(
            String target,
            Operator operator,
            String left,
            String right
    ) {
        this.target = target;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getTarget() {
        return target;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return threePadd(target)
                + " := "
                + threePadd(left)
                + threePadd(operator.value)
                + threePadd(right)
                + ";";
    }

    private String threePadd(String value) {
        if (value.length() > PADDING_SIZE) {
            throw new IllegalArgumentException("Padding impossible if value is too big");
        }

        StringBuilder sb = new StringBuilder(value);
        while(sb.length() < PADDING_SIZE) {
            sb.insert(0, " ");
        }

        return sb.toString();
    }
}
