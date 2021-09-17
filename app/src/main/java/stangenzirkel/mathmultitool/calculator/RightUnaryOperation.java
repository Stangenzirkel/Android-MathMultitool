package stangenzirkel.mathmultitool.calculator;

abstract public class RightUnaryOperation extends UnaryOperation {
    RightUnaryOperation(ExpressionNode node) {
        super(node);
    }

    public static String[] getAllUnaryOperators() {
        return new String[] {"%", "!"};
    }

    public static UnaryOperation getNode(String operator, ExpressionNode node) {
        switch (operator) {
            case "%":
                return new Percent(node);

            case "!":
                return new Factorial(node);

            default:
                throw new RuntimeException();
        }
    }
}

class Percent extends RightUnaryOperation {
    Percent(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        return a / 100;
    }
}

class Factorial extends RightUnaryOperation {
    Factorial(ExpressionNode node) {
        super(node);
    }

    @Override
    double function(double a) {
        double n = (int) a;
        double result = 1;

        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
