package com.werner.compiler;

public class Main {
    public static int getX() {
        int result;
        result = (4 + (2 * 8));
        return result;
    }

    public static void main(String[] args) {
        boolean x;
        x = (false != (false == true));
        String s;
        s = "hello world";
        int myInt;
        myInt = 42000;
        int result;
        result = 0;
        if ((x == true)) {
            result = 42;
        } else {
        }
        x = true;
        while ((x == false)) {
            result = (result + 4);
        }
        int calledResult;
        calledResult = getX();
        return;
    }
}
