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
        if (strings.length == 0) {
            return false;
        }
        for (String string: strings) {
            if (!isDigit(string) && !string.equals(".")) {
                return false;
            }
        }
        return true;
    }

    static ExpressionNode parse(String[] expressionParts) {
        Log.d(tag, "Start of parsing expression: ".concat(UsefulFunctions.joinString(Arrays.asList(expressionParts))));
        try {
            if (expressionParts.length == 0) {
                return new EmptyNode();
            }

            // return value of constant
            if (expressionParts.length == 1 && isConstant(expressionParts[0])) {
                return new Value(Value.getAllConstantsMap().get(expressionParts[0]));
            }

            // return float value
            if (isFloat(expressionParts)) {
                return new Value(Double.parseDouble(UsefulFunctions.joinString(Arrays.asList(expressionParts))));
            }

            // finding less priority operator
            int bracketLevel = 0;
            String operator = null;
            int index = -1;
            for (int i = 0; i < expressionParts.length; i++) {
                String cursor = expressionParts[i];
                if (cursor.equals("(")) {
                    bracketLevel++;
                } else if (cursor.equals(")")) {
                    bracketLevel--;
                } else if (isUnaryOperator(cursor)) {
                    if (operator == null) {
                        operator = cursor;
                        index = i;
                    }
                } else if (isBinaryOperator(cursor)) {
                    if (operator == null ||
                            isUnaryOperator(operator) ||
                            isBinaryOperator(operator) &&
                                    BinaryOperation.detPriority(operator) < BinaryOperation.detPriority(cursor)) {
                        operator = cursor;
                        index = i;
                    }
                }
            }

            // add brackets (((56 + 5 -> (((56 + 5)))
            if (bracketLevel > 0) {
                String[] newExpressionParts = new String[expressionParts.length + bracketLevel];
                System.arraycopy(expressionParts, 0, newExpressionParts, 0, expressionParts.length);
                for (int i = expressionParts.length; i < newExpressionParts.length; i++) {
                    newExpressionParts[i] = ")";
                }
                expressionParts = newExpressionParts;
            }


            if (operator == null) {
                // removes one bracket level
                return ExpressionNode.parse(Arrays.copyOfRange(expressionParts, 1, expressionParts.length - 1));
            } else if (isUnaryOperator(operator)) {
                return UnaryOperation.getNode(operator,
                        ExpressionNode.parse(Arrays.copyOfRange(expressionParts, index + 1, expressionParts.length)));
            } else if (isBinaryOperator(operator)) {
                return BinaryOperation.getNode(operator,
                        ExpressionNode.parse(Arrays.copyOfRange(expressionParts, 0, index)),
                        ExpressionNode.parse(Arrays.copyOfRange(expressionParts, index + 1, expressionParts.length)));
            }
        } catch (Throwable exception) {
            // error value
            Log.d(tag, "Error while parse ".concat(UsefulFunctions.joinString(Arrays.asList(expressionParts))));
            Log.d(tag, Log.getStackTraceString(exception));
            return new Value(0);
        }
        // error value
        Log.d(tag, "Error while parse ".concat(UsefulFunctions.joinString(Arrays.asList(expressionParts))));
        Log.d(tag, "Didn't catch exception");
        return new Value(0);
    }

    double getResult();

    ExpressionNode[] getNodes();
}
