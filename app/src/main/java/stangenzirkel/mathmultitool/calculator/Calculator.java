package stangenzirkel.mathmultitool.calculator;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private List<String> expressionParts = new ArrayList<>();
    private float buffer = 0;
    private boolean isRadianMod = true;

    public Calculator() {

    }

    public void addExpressionPart(String string) {
        expressionParts.add(string);
    }

    public void addExpressionPart(String string, int index) {
        expressionParts.add(index, string);
    }

    public float getResult() {
        return 0;
    }

    public String getStringResult() {
        return Float.toString(getResult());
    }

    public float getM() {
        return buffer;
    }

    public String getStringM() {
        return Float.toString(getM());
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();
        for(String s : expressionParts) {
            builder.append(s);
        }

        return builder.toString();
    }

    public void clear() {
        expressionParts.clear();
    }

    public void clearLast() {
        if (expressionParts.size() >= 1) {
            expressionParts.remove(expressionParts.size() - 1);
        }

    }

    public boolean isRadianMod() {
        return isRadianMod;
    }

    public void setRadianMod(boolean radianMod) {
        isRadianMod = radianMod;
    }
}
