package stangenzirkel.mathmultitool.calculator;

public abstract class BinaryOperation extends ExpressionNode {
    protected ExpressionNode leftNode;
    protected ExpressionNode rightNode;

    BinaryOperation(ExpressionNode leftNode, ExpressionNode rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[] {leftNode, rightNode};
    }

    public static String[] getAllBinaryOperators() {
        return new String[] {"^", "*", "/", "+", "-"};
    }

    public static int detPriority(String operator) {
        switch (operator) {
            case "+": case "-":
                return 1;

            case "*": case "/":
                return 2;

            case "^":
                return 3;

            default:
                throw new RuntimeException();
        }
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

    abstract double function(double a, double b);

    @Override
    public double getResult() {
        ExpressionNode a = leftNode;
        ExpressionNode b = rightNode;
        a.setParameters(parameters);
        b.setParameters(parameters);
        if (b instanceof EmptyNode) {
            return leftNode.getResult();
        }
        return function(a.getResult(), b.getResult());
    }
}

class Addition extends BinaryOperation {
    Addition(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    double function(double a, double b) {
        return a + b;
    }
}

class Subtraction extends BinaryOperation {
    Subtraction(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    double function(double a, double b) {
        return a - b;
    }
}

class Multiplication extends BinaryOperation {
    Multiplication(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    double function(double a, double b) {
        return a * b;
    }
}

class Division extends BinaryOperation {
    Division(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    double function(double a, double b) {
        return a / b;
    }
}

class Exponentiation extends BinaryOperation {
    Exponentiation(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    double function(double a, double b) {
        return Math.pow(a, b);
    }
}


