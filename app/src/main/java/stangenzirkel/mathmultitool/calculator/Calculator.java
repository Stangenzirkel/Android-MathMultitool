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

    // TODO remove canTypeSeparator
    private boolean canTypeSeparator = true;

    public Calculator() {
        expressionParts.add("0");
    }

    private String getLastExpressionPart() {
        return expressionParts.get(expressionParts.size() - 1);
    }

    // TODO refactor addOperator
    public boolean addOperator(String string) {
        // filtering invalid strings
        if (!ExpressionNode.isUnaryOperator(string) && !ExpressionNode.isBinaryOperator(string)) {
            Log.d(tag, "Operator addition failed: invalid string");
            return false;
        }

        // replacing first zero by unary minus
        if (string.equals("-") && expressionParts.size() == 1 && expressionParts.get(0).equals("0")) {
            expressionParts.set(0, "inverse");
            Log.d(tag, "Operator addition successful: replacing first zero by unary minus");
            canTypeSeparator = true;
            return true;
        }

        // can't type many unary minuses
        // for example: ------5
        if (string.equals("-") && expressionParts.size() != 0 && expressionParts.get(expressionParts.size() - 1).equals("inverse")) {
            Log.d(tag, "Operator addition failed: can't type many unary minuses");
            return false;
        }

        // replacing binary minus by unary minus
        if (string.equals("-") && expressionParts.size() != 0 &&
                (expressionParts.get(expressionParts.size() - 1).equals("(") ||
                        ExpressionNode.isBinaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        ExpressionNode.isUnaryOperator(expressionParts.get(expressionParts.size() - 1)))) {
            expressionParts.add("inverse");
            Log.d(tag, "Operator addition successful: replacing binary minus by unary minus");
            canTypeSeparator = true;
            return true;
        }

        // replacing single zero by digits or unary operators
        // for example: 6 * 0  -> 5 after 5 typed or 6 * 0 -> 6 * sin after sin typed
        if (expressionParts.size() != 0 && getLastExpressionPart().equals("0") &&
                ExpressionNode.isUnaryOperator(string) &&
                (expressionParts.size() == 1 || !(ExpressionNode.isDigit(expressionParts.get(expressionParts.size() - 2)) ||
                        expressionParts.get(expressionParts.size() - 2).equals(".")))) {
            expressionParts.set(expressionParts.size() - 1, string);
            Log.d(tag, "Operator addition successful: replacing single zero by digits or unary operators");
            canTypeSeparator = true;
            return true;
        }

        // can't type binary operator after other operator or "("
        // for example: 5+++1 or sin * 5 or (+7)
        if (ExpressionNode.isBinaryOperator(string) && expressionParts.size() != 0 &&
                !(ExpressionNode.isDigit(expressionParts.get(expressionParts.size() - 1)) ||
                        expressionParts.get(expressionParts.size() - 1).equals(")") ||
                        ExpressionNode.isConstant(expressionParts.get(expressionParts.size() - 1)) )) {
            Log.d(tag, "Operator addition failed: can't type binary operator after other operator or \"(\"");
            return false;
        }

        // can't type unary operator after ")" or digit
        // for example: ...)sin10 or 5sin6
        if (ExpressionNode.isUnaryOperator(string) && expressionParts.size() != 0 &&
                !(ExpressionNode.isUnaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        ExpressionNode.isBinaryOperator(expressionParts.get(expressionParts.size() - 1)) ||
                        expressionParts.get(expressionParts.size() - 1).equals("("))) {
            Log.d(tag, "Operator addition failed: can't type unary operator after \")\" or digit");
            return false;
        }

        // can't type anything except digits after "."
        // for example: 67, * 87,-
        if (expressionParts.get(expressionParts.size() - 1).equals(".") && !ExpressionNode.isDigit(string)) {
            Log.d(tag, "Operator addition failed: can't type anything except digits after \",\"");
            return false;
        }


        Log.d(tag, "Operator addition successful");
        expressionParts.add(string);
        canTypeSeparator = true;
        return true;
    }

    public boolean addDecimalSeparator() {
        // can't type "." after anything except digits
        // for example: -,
        if (expressionParts.size() == 0 || !ExpressionNode.isDigit(getLastExpressionPart())) {
            Log.d(tag, "DecimalSeparator addition failed: can't type \",\" after anything except digits");
            return false;
        }

        if (!canTypeSeparator) {
            Log.d(tag, "DecimalSeparator addition failed: canTypeSeparator is false");
            return false;
        }

        canTypeSeparator = false;
        Log.d(tag, "DecimalSeparator addition successful");
        expressionParts.add(".");
        return true;
    }

    public boolean addLeftBracket() {
        // replacing first zero by left Bracket added
        if (expressionParts.size() == 1 && getLastExpressionPart().equals("0")) {
            expressionParts.set(0, "(");
            Log.d(tag, "LeftBracket addition successful: replacing first zero by left Bracket added");
            canTypeSeparator = true;
            return true;
        }

        // can't type "(" after digits
        // for example: 6(6 + 1) or π(1 * 6)
        if (expressionParts.size() != 0 && !(ExpressionNode.isBinaryOperator(getLastExpressionPart()) ||
                ExpressionNode.isUnaryOperator(getLastExpressionPart()) ||
                        getLastExpressionPart().equals("("))) {
            Log.d(tag, "LeftBracket addition failed: can't type \"(\" after digits");
            return false;
        }

        Log.d(tag, "LeftBracket addition successful");
        expressionParts.add("(");
        canTypeSeparator = true;
        return true;
    }

    public boolean addRightBracket() {
        // can't type ")" without "("
        // for example: (1 + 6))
        if (Collections.frequency(expressionParts, "(") <= Collections.frequency(expressionParts, ")")) {
            Log.d(tag, "RightBracket addition failed: can't type \")\" without \"(\"");
            return false;
        }

        // can't type ")" after operators or "."
        // for example: (5 + 6,) or (5 + )
        if (!(getLastExpressionPart().equals(")") ||
                ExpressionNode.isDigit(getLastExpressionPart()) ||
                ExpressionNode.isConstant(getLastExpressionPart()))) {
            Log.d(tag, "RightBracket addition failed: can't type \")\" without \"(\"");
            return false;
        }

        Log.d(tag, "RightBracket addition successful");
        expressionParts.add(")");
        canTypeSeparator = true;
        return true;
    }

    public boolean addConstant(String string) {
        // filtering invalid strings
        if (!ExpressionNode.isConstant(string)) {
            Log.d(tag, "Constant addition failed: invalid string");
            return false;
        }

        // replacing single zero
        if (getLastExpressionPart().equals("0") &&
                (expressionParts.size() == 1
                 || !(expressionParts.get(expressionParts.size() - 2).equals(".") ||
                        ExpressionNode.isDigit(expressionParts.get(expressionParts.size() - 2))))) {
            Log.d(tag, "Constant addition successful: replacing single zero");
            expressionParts.set(expressionParts.size() - 1, string);
            canTypeSeparator = true;
            return true;
        }

        // can't type constant after ")" or digit
        // for example: ...)e or 5e
        if (expressionParts.size() != 0 &&
                !(ExpressionNode.isUnaryOperator(getLastExpressionPart()) ||
                        ExpressionNode.isBinaryOperator(getLastExpressionPart()) ||
                        getLastExpressionPart().equals("("))) {
            Log.d(tag, "Constant addition failed: can't type constant after \")\" or digit");
            return false;
        }

        // can't digit after constant
        // for example: e6
        if (ExpressionNode.isDigit(string) && expressionParts.size() != 0 && ExpressionNode.isConstant(expressionParts.get(expressionParts.size() - 1))) {
            Log.d(tag, "String addition failed: can't digit after constant");
            return false;
        }

        Log.d(tag, "Constant addition successful");
        expressionParts.add(string);
        canTypeSeparator = true;
        return true;
    }

    public boolean addDigit(String string) {
        // filtering invalid strings
        if (!ExpressionNode.isDigit(string)) {
            Log.d(tag, "Digit addition failed: invalid string");
            return false;
        }

        // replacing single zero
        if (getLastExpressionPart().equals("0") &&
                (expressionParts.size() == 1
                        || !(expressionParts.get(expressionParts.size() - 2).equals(".") ||
                        ExpressionNode.isDigit(expressionParts.get(expressionParts.size() - 2))))) {
            Log.d(tag, "Digit addition successful: replacing single zero");
            expressionParts.set(expressionParts.size() - 1, string);
            return true;
        }

        Log.d(tag, "Digit addition successful");
        expressionParts.add(string);
        return true;
    }

    public boolean addExpressionPart(String string) {
        if (ExpressionNode.isUnaryOperator(string) || ExpressionNode.isBinaryOperator(string)) {
            return addOperator(string);
        } else if (string.equals(".")) {
            return addDecimalSeparator();
        } else if (string.equals("(")) {
            return addLeftBracket();
        } else if (string.equals(")")) {
            return addRightBracket();
        } else if (ExpressionNode.isConstant(string)) {
            return addConstant(string);
        } else if (ExpressionNode.isDigit(string)) {
            return addDigit(string);
        } else {
            return false;
        }
    }

    public double getResult() {
        return ExpressionNode.parse(expressionParts.toArray(new String[expressionParts.size()])).getResult();
    }

    public String getStringResult() {
        try {
            return Double.toString(getResult());
        } catch (Throwable e) {
            return "Error";
        }

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
        canTypeSeparator = true;
    }

    public void clearLast() {
        if (expressionParts.size() != 0) {
            if (getLastExpressionPart().equals(".")) {
                canTypeSeparator = true;
            }
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
