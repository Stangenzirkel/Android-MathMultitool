package stangenzirkel.mathmultitool.calculator;

public class EmptyNode implements ExpressionNode{
    @Override
    public double getResult() {
        return 0;
    }

    @Override
    public ExpressionNode[] getNodes() {
        return new ExpressionNode[0];
    }
}
