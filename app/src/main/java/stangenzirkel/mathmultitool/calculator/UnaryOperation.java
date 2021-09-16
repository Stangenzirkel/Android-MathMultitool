package stangenzirkel.mathmultitool.calculator;

public abstract class UnaryOperation implements ExpressionNode {
    protected ExpressionNode rightNode;
    // private Function<Double, Double> function;

    UnaryOperation(ExpressionNode node) {
        this.rightNode = node;
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[] {rightNode};
    }

    public static String[] getAllUnaryOperators() {
        return new String[] {"inverse", "root"};
    }

    public static UnaryOperation getNode(String operator, ExpressionNode rightNode) {
        switch (operator) {
            case "inverse":
                return new Inverse(rightNode);

            case "root":
                return new SqRt(rightNode);

            default:
                throw new RuntimeException();
        }
    }
}

class Inverse extends UnaryOperation {
    Inverse(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return -rightNode.getResult();
    }
}

class SqRt extends UnaryOperation {
    SqRt(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.sqrt(rightNode.getResult());
    }
}


