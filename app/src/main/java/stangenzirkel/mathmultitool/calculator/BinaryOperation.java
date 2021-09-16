package stangenzirkel.mathmultitool.calculator;

public abstract class BinaryOperation implements ExpressionNode {
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
}

class Addition extends BinaryOperation {
    Addition(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    public double getResult() {
        return leftNode.getResult() + rightNode.getResult();
    }
}

class Subtraction extends BinaryOperation {
    Subtraction(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    public double getResult() {
        return leftNode.getResult() - rightNode.getResult();
    }
}

class Multiplication extends BinaryOperation {
    Multiplication(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    public double getResult() {
        if (rightNode instanceof EmptyNode) {
            return leftNode.getResult();
        }
        return leftNode.getResult() * rightNode.getResult();
    }
}

class Division extends BinaryOperation {
    Division(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    public double getResult() {
        if (rightNode instanceof EmptyNode) {
            return leftNode.getResult();
        }
        return leftNode.getResult() / rightNode.getResult();
    }
}

class Exponentiation extends BinaryOperation {
    Exponentiation(ExpressionNode leftNode, ExpressionNode rightNode) {
        super(leftNode, rightNode);
    }

    @Override
    public double getResult() {
        if (rightNode instanceof EmptyNode) {
            return leftNode.getResult();
        }
        return Math.pow(leftNode.getResult(), rightNode.getResult());
    }
}


