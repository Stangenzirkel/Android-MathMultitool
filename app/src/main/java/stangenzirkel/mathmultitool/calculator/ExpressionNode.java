package stangenzirkel.mathmultitool.calculator;

import java.lang.reflect.Array;
import java.util.Map;

interface ExpressionNode {
    static ExpressionNode parse(String[] strings) {
        Map<String, Double> constants = Value.getConstant();
        for (String key: constants.keySet()) {
            if (Array.getLength(strings) == 1 && strings[0].equals(key)) {
                return new Value(constants.get(key));
            }
        }

        return new Value(0);
    }

    double getResult();

    ExpressionNode[] getNodes();
}
