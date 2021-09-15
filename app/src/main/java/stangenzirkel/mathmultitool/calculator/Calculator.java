package stangenzirkel.mathmultitool.calculator;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Calculator {
    private List<String> expressionParts = new ArrayList<>();
    private double buffer = 0;
    private boolean isRadianMod = true;
    public static String tag = "CalculatorTag";

    public Calculator() {
        expressionParts.add("0");
    }

    private boolean isDigit(String s) {
        boolean result = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}).contains(s);
        Log.d(tag, "isDigit arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    private boolean isConstant(String s) {
        boolean result = Value.getConstant().keySet().contains(s);
        Log.d(tag, "isConstant arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    private boolean isUnaryOperator(String s) {
        boolean result = Arrays.asList(UnaryOperation.getAllUnaryOperators()).contains(s);
        Log.d(tag, "isUnaryOperator arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    private boolean isBinaryOperator(String s) {
        boolean result = Arrays.asList(BinaryOperation.getAllBinaryOperators()).contains(s);
        Log.d(tag, "isBinaryOperator arg: ".concat(s).concat(" result: ").concat(Boolean.toString(result)));
        return result;
    }

    public boolean addExpressionPart(String string) {
        // filtering invalid strings
        // for example: 5 + aboba
        if (!(isUnaryOperator(string) ||
                isBinaryOperator(string) ||
                isConstant(string) ||
                isDigit(string) ||
                string.equals(",") ||
                string.equals("(") ||
                string.equals(")"))) {
            Log.d(tag, "String addition failed: invalid string");
            return false;
        }

        // replacing first zero by unary minus
        if (string.equals("-") && expressionParts.size() == 1 && expressionParts.get(0).equals("0")) {
            expressionParts.set(0, "inverse");
            Log.d(tag, "String addition successful: replacing first zero by unary minus");
            return true;
        }

        // can't type many unary minuses
        // for example: ------5
        if (string.equals("-") && expressionParts.size() != 0 && expressionParts.get(expressionParts.size() - 1).equals("inverse")) {
            Log.d(tag, "String addition failed: can't type many unary minuses");
            return false;
        }

        // replacing binary minus by unary minus
        if (string.equals("-") && expressionParts.size() != 0 &&
                (expressionParts.get(expressionParts.size() - 1).equals("(") ||
                        isBinaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                                isUnaryOperator(expressionParts.get(expressionParts.size() - 1)))) {
            expressionParts.add("inverse");
            Log.d(tag, "String addition successful: replacing binary minus by unary minus");
            return true;
        }

        // replacing single zero by digits or unary operators
        // for example: 6 * 0  -> 5 after 5 typed or 6 * 0 -> 6 * sin after sin typed
        if (expressionParts.size() != 0 && expressionParts.get(expressionParts.size() - 1).equals("0") &&
                (isDigit(string) || isConstant(string) || isUnaryOperator(string)) &&
                (expressionParts.size() == 1 || !(isDigit(expressionParts.get(expressionParts.size() - 2)) ||
                        expressionParts.get(expressionParts.size() - 2).equals(",")))) {
            expressionParts.set(expressionParts.size() - 1, string);
            Log.d(tag, "String addition successful: replacing single zero by digits or unary operators");
            return true;
        }

        // can't type binary operator after other operator or "("
        // for example: 5+++1 or sin * 5 or (+7)
        if (isBinaryOperator(string) && expressionParts.size() != 0 &&
                !(isDigit(expressionParts.get(expressionParts.size() - 1)) ||
                        expressionParts.get(expressionParts.size() - 1).equals(")") ||
                        isConstant(expressionParts.get(expressionParts.size() - 1)) )) {
            Log.d(tag, "String addition failed: can't type binary operator after other operator or \"(\"");
            return false;
        }

        // can't type unary operator after ")" or digit
        // for example: ...)sin10 or 5sin6
        if (isUnaryOperator(string) && expressionParts.size() != 0 &&
                !(isUnaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        isBinaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        expressionParts.get(expressionParts.size() - 1).equals("("))) {
            Log.d(tag, "String addition failed: can't type unary operator after \")\" or digit");
            return false;
        }

        // can't type constant after ")" or digit
        // for example: ...)e or 5e
        if (isConstant(string) && expressionParts.size() != 0 &&
                !(isUnaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        isBinaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        expressionParts.get(expressionParts.size() - 1).equals("("))) {
            Log.d(tag, "String addition failed: can't type constant after \")\" or digit");
            return false;
        }

        // can't type anything except digits after ","
        // for example: 67, * 87,-
        if (expressionParts.get(expressionParts.size() - 1).equals(",") && !isDigit(string)) {
            Log.d(tag, "String addition failed: can't type anything except digits after \",\"");
            return false;
        }

        // can't type "," after anything except digits
        // for example: -,
        if (!isDigit(expressionParts.get(expressionParts.size() - 1)) && string.equals(",")) {
            Log.d(tag, "String addition failed: can't type \",\" after anything except digits");
            return false;
        }

        // replacing first zero by left scope added
        if (expressionParts.size() == 1 && expressionParts.get(0).equals("0") && string.equals("(")) {
            expressionParts.set(0, string);
            Log.d(tag, "String addition successful: replacing first zero by left scope added");
            return true;
        }

        // can't type ")" without "("
        // for example: (1 + 6))
        if (string.equals(")") && Collections.frequency(expressionParts, "(") <= Collections.frequency(expressionParts, ")")) {
            Log.d(tag, "String addition failed: can't type \")\" without \"(\"");
            return false;
        }

        // can't type ")" after operators or ","
        // for example: (5 + 6,) or (5 + )
        if (string.equals(")") &&
                !(expressionParts.get(expressionParts.size() - 1).equals(")") ||
                isDigit(expressionParts.get(expressionParts.size() - 1)) ||
                        isConstant(expressionParts.get(expressionParts.size() - 1)))) {
            Log.d(tag, "String addition failed: can't type \")\" without \"(\"");
            return false;
        }

        // can't type "(" after digits
        // for example: 6(6 + 1) or π(1 * 6)
        if (string.equals("(") && expressionParts.size() != 0 &&
                !(isBinaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        isUnaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        expressionParts.get(expressionParts.size() - 1).equals("("))) {
            Log.d(tag, "String addition failed: can't type \"(\" after digits");
            return false;
        }

        // can't digit after constant
        // for example: e6
        if (isDigit(string) && expressionParts.size() != 0 && isConstant(expressionParts.get(expressionParts.size() - 1))) {
            Log.d(tag, "String addition failed: can't digit after constant");
            return false;
        }


        Log.d(tag, "String addition successful");
        expressionParts.add(string);
        return true;
    }

    public boolean addExpressionPart(String string, int index) {
        return false;
    }

    public double getResult() {
        return ExpressionNode.parse(expressionParts.toArray(new String[expressionParts.size()])).getResult();
    }

    public String getStringResult() {
        return Double.toString(getResult());
    }

    public double getM() {
        return buffer;
    }

    public String getStringM() {
        return Double.toString(getM());
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();
        for(String s : expressionParts) {
            builder.append(s);
        }

        return builder.toString()
                .replace("pi", "π")
                .replace("root", "√")
                .replace("inverse", "-");
    }

    public void clear() {
        expressionParts.clear();
        expressionParts.add("0");
    }

    public void clearLast() {
        if (expressionParts.size() != 0) {
            expressionParts.remove(expressionParts.size() - 1);
        }

        if (expressionParts.size() == 0) {
            expressionParts.add("0");
        }
    }

    public boolean isRadianMod() {
        return isRadianMod;
    }

    public void setRadianMod(boolean radianMod) {
        isRadianMod = radianMod;
    }
}
