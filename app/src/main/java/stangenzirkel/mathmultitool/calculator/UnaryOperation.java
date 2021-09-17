package stangenzirkel.mathmultitool.calculator;

public abstract class UnaryOperation extends ExpressionNode {
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

    @Override
    double getResult() {
        ExpressionNode node = this.node;
        node.setParameters(parameters);
        return function(node.getResult());
    }

    abstract double function(double a);
}

class Inverse extends UnaryOperation {
    Inverse(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return -a;
    }
}

class SqRt extends UnaryOperation {
    SqRt(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.sqrt(a);
    }
}

class Sin extends UnaryOperation {
    Sin(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        if (parameters.containsKey("isRadianMod") && !Boolean.valueOf(parameters.get("isRadianMod"))) {
            a = Math.toRadians(a);
        }
        return Math.sin(a);
    }
}

class Cos extends UnaryOperation {
    Cos(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        if (parameters.containsKey("isRadianMod") && !Boolean.valueOf(parameters.get("isRadianMod"))) {
            a = Math.toRadians(a);
        }
        return Math.cos(a);
    }
}

class Tan extends UnaryOperation {
    Tan(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        if (parameters.containsKey("isRadianMod") && !Boolean.valueOf(parameters.get("isRadianMod"))) {
            a = Math.toRadians(a);
        }
        return Math.tan(a);
    }
}

class ASin extends UnaryOperation {
    ASin(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.asin(a);
    }
}

class ACos extends UnaryOperation {
    ACos(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.acos(a);
    }
}

class ATan extends UnaryOperation {
    ATan(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.atan(a);
    }
}

class Lg extends UnaryOperation {
    Lg(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.log10(a);
    }
}

class Ln extends UnaryOperation {
    Ln(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.log(a);
    }
}

class RadToDeg extends UnaryOperation {
    RadToDeg(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.toDegrees(a);
    }
}

class DegToRad extends UnaryOperation {
    DegToRad(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return Math.toRadians(a);
    }
}


