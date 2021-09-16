package stangenzirkel.mathmultitool.calculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public abstract class UnaryOperation implements ExpressionNode {
    private ExpressionNode rightNode;
    private Function<Double, Double> function;

    UnaryOperation(ExpressionNode node) {
        this.rightNode = node;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public double getResult() {
        return function.apply(rightNode.getResult());
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
    private final Function<Double, Double> function = aDouble -> -aDouble;
    Inverse(ExpressionNode node) {
        super(node);
    }
}

class SqRt extends UnaryOperation {
    private final Function<Double, Double> function = Math::sqrt;
    SqRt(ExpressionNode node) {
        super(node);
    }
}


