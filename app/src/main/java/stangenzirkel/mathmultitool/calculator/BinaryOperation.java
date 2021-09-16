package stangenzirkel.mathmultitool.calculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.BiFunction;
import java.util.function.Function;

public class BinaryOperation implements ExpressionNode {
    private ExpressionNode leftNode;
    private ExpressionNode rightNode;
    private BiFunction<Double, Double, Double> function;

    BinaryOperation(ExpressionNode leftNode, ExpressionNode rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public double getResult() {
        return function.apply(leftNode.getResult(), rightNode.getResult());
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[] {leftNode, rightNode};
    }

    public static String[] getAllBinaryOperators() {
        return new String[] {"-", "+", "*", "/", "^"};
    }

    public static BinaryOperation getNode(String operator, ExpressionNode leftNode, ExpressionNode rightNode) {
        switch (operator) {
            case "+":
                return new Addition(leftNode, rightNode);

            case "-":
                return new Subtraction(leftNode, rightNode);

            case "*":
                return new Multiplication(leftNode, rightNode);

            case "/":
                return new Division(leftNode, rightNode);

            case "^":
                return new Exponentiation(leftNode, rightNode);

            default:
                throw new RuntimeException();
        }
    }
}

class Addition extends BinaryOperation {
    private final BiFunction<Double, Double, Double> function =  (aDouble, aDouble2) -> aDouble + aDouble2;
    Addition(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }
}

class Subtraction extends BinaryOperation {
    private final BiFunction<Double, Double, Double> function =  (aDouble, aDouble2) -> aDouble - aDouble2;
    Subtraction(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }
}

class Multiplication extends BinaryOperation {
    private final BiFunction<Double, Double, Double> function =  (aDouble, aDouble2) -> aDouble * aDouble2;
    Multiplication(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }
}

class Division extends BinaryOperation {
    private final BiFunction<Double, Double, Double> function =  (aDouble, aDouble2) -> aDouble / aDouble2;
    Division(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }
}

class Exponentiation extends BinaryOperation {
    private final BiFunction<Double, Double, Double> function = Math::pow;
    Exponentiation(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }
}


