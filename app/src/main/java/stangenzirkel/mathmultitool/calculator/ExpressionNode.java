package stangenzirkel.mathmultitool.calculator;

import android.util.Log;
import java.util.Arrays;

import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

interface ExpressionNode {
    String tag = Calculator.tag;
    static boolean isDigit(String s) {
        boolean result = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}).contains(s);
        Log.d(tag, "isDigit arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    static boolean isConstant(String s) {
        boolean result = Value.getAllConstantsMap().containsKey(s);
        Log.d(tag, "isConstant arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    static boolean isUnaryOperator(String s) {
        boolean result = Arrays.asList(UnaryOperation.getAllUnaryOperators()).contains(s);
        Log.d(tag, "isUnaryOperator arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    static boolean isBinaryOperator(String s) {
        boolean result = Arrays.asList(BinaryOperation.getAllBinaryOperators()).contains(s);
        Log.d(tag, "isBinaryOperator arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    static boolean isFloat(String[] strings) {
        for (String string: strings) {
            if (!isDigit(string) && !string.equals(".")) {
                return false;
            }
        }
        return true;
    }

    static ExpressionNode parse(String[] strings) {
        // return value of constant
        if (strings.length == 1 && isConstant(strings[0])) {
            return new Value(Value.getAllConstantsMap().get(strings[0]));
        }

        if (isFloat(strings)) {
            return new Value(Double.parseDouble(UsefulFunctions.joinString(Arrays.asList(strings))));
        }
        return new Value(0);
    }

    double getResult();

    ExpressionNode[] getNodes();
}
