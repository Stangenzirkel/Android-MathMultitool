package stangenzirkel.mathmultitool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import stangenzirkel.mathmultitool.calculator.Expression;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class CalculatorDB {
    private static final CalculatorDB calculatorDB = new CalculatorDB();
    private Context context;
    public static final String tag = "CalculatorDBTag";

    private CalculatorDB() {}

    public void setContext(Context context) {
        this.context = context;
    }

    public static CalculatorDB getInstance() {
        return calculatorDB;
    }

    public void addExpression(Expression expression) {
        Log.d(tag, "Adding expression to DB:");
        Log.d(CalculatorDB.tag, expression.toString());
        ContentValues cv = new ContentValues();

        cv.put("time", expression.getTime());
        cv.put("expression", UsefulFunctions.joinString("|", expression.getExpressionParts()));
        cv.put("result", expression.getResult());

        new DBHelper(context).getWritableDatabase().insert("expressions", null, cv);
    }

    public Expression[] getExpressions() {
        Log.d(tag, "Getting expressions from DB:");
        Cursor c =  new DBHelper(context).getWritableDatabase().query("expressions", null, null, null, null, null, null);
        Expression[] expressions = new Expression[c.getCount()];

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int timeColIndex = c.getColumnIndex("time");
            int expressionColIndex = c.getColumnIndex("expression");
            int resultColIndex = c.getColumnIndex("result");
            int i = 0;

            do {
                expressions[i] = new Expression(c.getInt(idColIndex),
                        c.getLong(timeColIndex),
                        c.getString(expressionColIndex).split("\\|"),
                        c.getString(resultColIndex));
                i++;
            } while (c.moveToNext());
        }
        c.close();

        for (Expression expression: expressions) {
            Log.d(tag, expression.toString());
        }
        return expressions;
    }
}
