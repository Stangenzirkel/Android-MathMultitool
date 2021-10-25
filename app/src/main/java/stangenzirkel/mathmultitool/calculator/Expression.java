package stangenzirkel.mathmultitool.calculator;

import java.util.Arrays;

public class Expression {
    public int getId() {
        return id;
    }

    private int id;
    private long time;
    private String[] expressionParts;
    private String result;

//    public Expression(long time, String[] expressionParts, String result) {
//        new Expression(0, time, expressionParts, result);
//    }

    public Expression(int id, long time, String[] expressionParts, String result) {
        this.id = id;
        this.time = time;
        this.expressionParts = expressionParts;
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String[] getExpressionParts() {
        return expressionParts;
    }

    public void setExpressionParts(String[] expressionParts) {
        this.expressionParts = expressionParts;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "id=" + id +
                ", time=" + time +
                ", expressionParts=" + Arrays.toString(expressionParts) +
                ", result='" + result + '\'' +
                '}';
    }
}
