package stangenzirkel.mathmultitool.calculator;

import java.util.HashMap;
import java.util.Map;

class Value implements ExpressionNode {
    private double value;

    public Value(double value) {
        this.value = value;
    }

    @Override
    public double getResult() {
        return value;
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[0];
    }

    static Map<String, Double> getConstant() {
        Map<String, Double> constant = new HashMap<>();
        constant.put("pi", Math.PI);
        constant.put("e", Math.E);

        return constant;
    }
}
