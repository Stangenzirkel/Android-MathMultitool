package stangenzirkel.mathmultitool.calculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public abstract class UnaryOperation implements ExpressionNode {
    private ExpressionNode leftNode;
    private Function<Double, Double> function;

    UnaryOperation(ExpressionNode node) {
        this.leftNode = node;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public double getResult() {
        return function.apply(leftNode.getResult());
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[] {leftNode};
    }

    public static String[] getAllUnaryOperators() {
        return new String[] {"inverse", "root"};
    }
}

class Inverse extends UnaryOperation {
    private Function<Double, Double> function = aDouble -> -aDouble;
    Inverse(ExpressionNode node) {
        super(node);
    }
}

class SqRt extends UnaryOperation {
    private Function<Double, Double> function = aDouble -> Math.sqrt(aDouble);
    SqRt(ExpressionNode node) {
        super(node);
    }
}


