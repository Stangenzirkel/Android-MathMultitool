package stangenzirkel.mathmultitool.calculator;

public abstract class UnaryOperation implements ExpressionNode {
    protected ExpressionNode node;
    // private Function<Double, Double> function;

    UnaryOperation(ExpressionNode node) {
        this.node = node;
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[] {node};
    }

    public static String[] getAllUnaryOperators() {
        return new String[] {"inverse",
                "root",
                "sin",
                "cos",
                "tan",
                "asin",
                "acos",
                "atan",
                "lg",
                "ln",
                "rtg",
                "dtr"};
    }

    public static UnaryOperation getNode(String operator, ExpressionNode node) {
        // TODO replace by hashmap
        switch (operator) {
            case "inverse":
                return new Inverse(node);

            case "root":
                return new SqRt(node);

            case "sin":
                return new Sin(node);

            case "cos":
                return new Cos(node);

            case "tan":
                return new Tan(node);

            case "asin":
                return new ASin(node);

            case "acos":
                return new ACos(node);

            case "atan":
                return new ATan(node);

            case "lg":
                return new Lg(node);

            case "ln":
                return new Ln(node);

            case "rtd":
                return new RadToDeg(node);

            case "dtr":
                return new DegToRad(node);

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
        return -node.getResult();
    }
}

class SqRt extends UnaryOperation {
    SqRt(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.sqrt(node.getResult());
    }
}

class Sin extends UnaryOperation {
    Sin(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.sin(node.getResult());
    }
}

class Cos extends UnaryOperation {
    Cos(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.cos(node.getResult());
    }
}

class Tan extends UnaryOperation {
    Tan(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.tan(node.getResult());
    }
}

class ASin extends UnaryOperation {
    ASin(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.asin(node.getResult());
    }
}

class ACos extends UnaryOperation {
    ACos(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.acos(node.getResult());
    }
}

class ATan extends UnaryOperation {
    ATan(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.atan(node.getResult());
    }
}

class Lg extends UnaryOperation {
    Lg(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.log10(node.getResult());
    }
}

class Ln extends UnaryOperation {
    Ln(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.log(node.getResult());
    }
}

class RadToDeg extends UnaryOperation {
    RadToDeg(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.toDegrees(node.getResult());
    }
}

class DegToRad extends UnaryOperation {
    DegToRad(ExpressionNode node) {
        super(node);
    }

    @Override
    public double getResult() {
        return Math.toRadians(node.getResult());
    }
}


