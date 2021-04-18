package Calc;

public final class MathCalc {

    final static String nan = "NaN";
    final static String infinity = "Infinity";

    public static Double plus( Double[] arrayNumbers ) {
        return arrayNumbers[0] + arrayNumbers[1];
    }

    public static Double minus( Double[] arrayNumbers ) {
        return arrayNumbers[0] - arrayNumbers[1];
    }

    public static Double multiplying( Double[] arrayNumbers ) {
        return arrayNumbers[0] * arrayNumbers[1];
    }

    //Проверка деления на ноль
    public static void division( Double[] arrayNumbers ) {

        if (arrayNumbers[1] == 0) {
            vernutOshibku(arrayNumbers);
        } else {
            divisionTrue(arrayNumbers);
        }
    }

    public static Double divisionTrue( Double[] arrayNumbers ) {
        return arrayNumbers[0] / arrayNumbers[1];
    }

    public static String vernutOshibku( Double[] arrayNumbers ) {
        if (arrayNumbers[0] == 0) {
            return nan;
        } else {
            return infinity;
        }
    }

}
