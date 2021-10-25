package stangenzirkel.mathmultitool;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import stangenzirkel.mathmultitool.calculator.Calculator;
import stangenzirkel.mathmultitool.calculator.Expression;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class CalculatorFragment extends Fragment implements View.OnClickListener {
    private Calculator calculator = Calculator.getInstanse();
    private static final String tag = "CalculatorFragmentTag";
    private static final String memoryKey = "memoryKey", expressionKey = "expressionKey";

    public CalculatorFragment() {}

    interface Callback {
        void onCalculatorFragmentDBButtonClick();
    }

    public static CalculatorFragment newInstance() {
        CalculatorFragment fragment = new CalculatorFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        setHasOptionsMenu(true);

        loadBuffer();
        loadExpression();

        root.findViewById(R.id.kl_3b).setVisibility(View.GONE);
        root.findViewById(R.id.kl_4b).setVisibility(View.GONE);
        root.findViewById(R.id.kl_5b).setVisibility(View.GONE);
        root.findViewById(R.id.kl_6b).setVisibility(View.GONE);

        root.findViewById(R.id.btn_rad_deg).setOnClickListener(this);

        root.findViewById(R.id.btn_main_keyboard).setOnClickListener(this);
        root.findViewById(R.id.btn_function_keyboard).setOnClickListener(this);

        root.findViewById(R.id.btn_0).setOnClickListener(this);
        root.findViewById(R.id.btn_1).setOnClickListener(this);
        root.findViewById(R.id.btn_2).setOnClickListener(this);
        root.findViewById(R.id.btn_3).setOnClickListener(this);
        root.findViewById(R.id.btn_4).setOnClickListener(this);
        root.findViewById(R.id.btn_5).setOnClickListener(this);
        root.findViewById(R.id.btn_6).setOnClickListener(this);
        root.findViewById(R.id.btn_7).setOnClickListener(this);
        root.findViewById(R.id.btn_8).setOnClickListener(this);
        root.findViewById(R.id.btn_9).setOnClickListener(this);

        root.findViewById(R.id.btn_c).setOnClickListener(this);
        root.findViewById(R.id.btn_del).setOnClickListener(this);
        root.findViewById(R.id.btn_result).setOnClickListener(this);

        root.findViewById(R.id.btn_mc).setOnClickListener(this);
        root.findViewById(R.id.btn_mplus).setOnClickListener(this);
        root.findViewById(R.id.btn_mminus).setOnClickListener(this);
        root.findViewById(R.id.btn_mr).setOnClickListener(this);

        root.findViewById(R.id.btn_left_bracket).setOnClickListener(this);
        root.findViewById(R.id.btn_right_bracket).setOnClickListener(this);
        root.findViewById(R.id.btn_pi).setOnClickListener(this);
        root.findViewById(R.id.btn_e).setOnClickListener(this);

        root.findViewById(R.id.btn_plus).setOnClickListener(this);
        root.findViewById(R.id.btn_minus).setOnClickListener(this);
        root.findViewById(R.id.btn_multiplication).setOnClickListener(this);
        root.findViewById(R.id.btn_division).setOnClickListener(this);
        root.findViewById(R.id.btn_exponentiation).setOnClickListener(this);
        root.findViewById(R.id.btn_root).setOnClickListener(this);
        root.findViewById(R.id.btn_dot).setOnClickListener(this);

        root.findViewById(R.id.btn_sin).setOnClickListener(this);
        root.findViewById(R.id.btn_cos).setOnClickListener(this);
        root.findViewById(R.id.btn_tan).setOnClickListener(this);
        root.findViewById(R.id.btn_asin).setOnClickListener(this);
        root.findViewById(R.id.btn_acos).setOnClickListener(this);
        root.findViewById(R.id.btn_atan).setOnClickListener(this);

        root.findViewById(R.id.btn_rtd).setOnClickListener(this);
        root.findViewById(R.id.btn_dtr).setOnClickListener(this);
        root.findViewById(R.id.btn_lg).setOnClickListener(this);
        root.findViewById(R.id.btn_ln).setOnClickListener(this);
        root.findViewById(R.id.btn_10exp).setOnClickListener(this);
        root.findViewById(R.id.btn_percent).setOnClickListener(this);
        root.findViewById(R.id.btn_factorial).setOnClickListener(this);

        root.findViewById(R.id.btn_f1).setOnClickListener(this);
        root.findViewById(R.id.btn_f2).setOnClickListener(this);
        root.findViewById(R.id.btn_f3).setOnClickListener(this);
        root.findViewById(R.id.btn_f4).setOnClickListener(this);
        // root.findViewById(R.id.btn_percent).setOnClickListener(this);

        TextView tv = root.findViewById(R.id.tv_calculator_expression);
        tv.setText(calculator.getExpressionString());

        tv = root.findViewById(R.id.tv_calculator_result);
        tv.setText(calculator.getStringResult());
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calculator_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_exp_item:
                addCurrentExpressionToDB();
                break;

            case R.id.show_saved_exp_item:
                printBD();
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).onCalculatorFragmentDBButtonClick();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        Log.d(tag, "Click on ".concat((String) button.getText()));
        switch (v.getId()) {
            case R.id.btn_main_keyboard:
                getView().findViewById(R.id.kl_3b).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_4b).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_5b).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_6b).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_3).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.kl_4).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.kl_5).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.kl_6).setVisibility(View.VISIBLE);
                break;

            case R.id.btn_function_keyboard:
                getView().findViewById(R.id.kl_3).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_4).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_5).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_6).setVisibility(View.GONE);
                getView().findViewById(R.id.kl_3b).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.kl_4b).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.kl_5b).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.kl_6b).setVisibility(View.VISIBLE);
                break;

            case R.id.btn_1:
                calculator.addDigit("1");
                break;

            case R.id.btn_2:
                calculator.addDigit("2");
                break;

            case R.id.btn_3:
                calculator.addDigit("3");
                break;

            case R.id.btn_4:
                calculator.addDigit("4");
                break;

            case R.id.btn_5:
                calculator.addDigit("5");
                break;

            case R.id.btn_6:
                calculator.addDigit("6");
                break;

            case R.id.btn_7:
                calculator.addDigit("7");
                break;

            case R.id.btn_8:
                calculator.addDigit("8");
                break;

            case R.id.btn_9:
                calculator.addDigit("9");
                break;

            case R.id.btn_0:
                calculator.addDigit("0");
                break;

            case R.id.btn_result: case R.id.btn_resultb:
                addCurrentExpressionToDB();
                printBD();
                String result = calculator.getStringResult();
                if (result.endsWith(".0")) {
                    result = result.replace(".0", "");
                }
                calculator.clearAll();
                for (char elem: result.toCharArray()) {
                    calculator.addExpressionPart(String.valueOf(elem));
                }
                break;

            case R.id.btn_c:
                calculator.clearAll();
                break;

            case R.id.btn_del:
                calculator.clearLast();
                break;

            case R.id.btn_left_bracket:
                calculator.addLeftBracket();
                break;

            case R.id.btn_right_bracket:
                calculator.addRightBracket();
                break;

            case R.id.btn_plus:
                calculator.addOperator("+");
                break;

            case R.id.btn_minus:
                calculator.addOperator("-");
                break;

            case R.id.btn_multiplication:
                calculator.addOperator("*");
                break;

            case R.id.btn_division:
                calculator.addOperator("/");
                break;

            case R.id.btn_exponentiation:
                calculator.addOperator("^");
                break;

            case R.id.btn_root:
                calculator.addOperator("root");
                break;

            case R.id.btn_pi:
                calculator.addConstant("pi");
                break;

            case R.id.btn_e:
                calculator.addConstant("e");
                break;

            case R.id.btn_percent:
                calculator.addOperator("%");
                break;

            case R.id.btn_factorial:
                calculator.addOperator("!");
                break;

            case R.id.btn_sin:
                calculator.addOperator("sin");
                calculator.addLeftBracket();
                break;

            case R.id.btn_cos:
                calculator.addOperator("cos");
                calculator.addLeftBracket();
                break;

            case R.id.btn_tan:
                calculator.addOperator("tan");
                calculator.addLeftBracket();
                break;

            case R.id.btn_asin:
                calculator.addOperator("asin");
                calculator.addLeftBracket();
                break;

            case R.id.btn_acos:
                calculator.addOperator("acos");
                calculator.addLeftBracket();
                break;

            case R.id.btn_atan:
                calculator.addOperator("atan");
                calculator.addLeftBracket();
                break;

            case R.id.btn_lg:
                calculator.addOperator("lg");
                break;

            case R.id.btn_ln:
                calculator.addOperator("ln");
                break;

            case R.id.btn_rtd:
                calculator.addOperator("rtd");
                calculator.addLeftBracket();
                break;

            case R.id.btn_dtr:
                calculator.addOperator("dtr");
                calculator.addLeftBracket();
                break;

            case R.id.btn_10exp:
                if (calculator.addDigit("1") && calculator.addDigit("0")) {
                    calculator.addOperator("^");
                }
                break;

            case R.id.btn_dot:
                calculator.addDecimalSeparator();
                break;

            case R.id.btn_mc:
                calculator.setBuffer(0);
                Toast.makeText(getContext(), "Memory cleared", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_mplus:
                if (calculator.addToBuffer()) {
                    Toast.makeText(getContext(), "Result added to memory", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_mminus:
                if (calculator.subtractFromBuffer()) {
                    Toast.makeText(getContext(), "Result subtracted from memory", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_mr:
                String string = Double.toString(calculator.getBuffer());
                if (string.endsWith(".0")) {
                    string = string.replace(".0", "");
                }

                calculator.clearAll();
                for (String s: string.split("")) {
                    calculator.addExpressionPart(s);
                }
                Toast.makeText(getContext(), "Memory read", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_rad_deg: //TODO add deg/rad mods to calculator
                calculator.setRadianMod(!calculator.isRadianMod());

                Button btn = (Button) getView().findViewById(R.id.btn_rad_deg);
                if (calculator.isRadianMod()) {
                    btn.setText("deg");
                } else {
                    btn.setText("rad");
                }
                break;
        }

        TextView tv = getView().findViewById(R.id.tv_calculator_expression);
        tv.setText(calculator.getExpressionString());

        tv = getView().findViewById(R.id.tv_calculator_result);
        tv.setText(calculator.getStringResult());

        saveBuffer();
        saveExpression();
    }

    private void saveBuffer() {
        String value = Double.toString(calculator.getBuffer());
        Editor editor = getActivity().getPreferences(Activity.MODE_PRIVATE).edit();
        editor.putString(memoryKey, value);
        editor.apply();
    }

    private void loadBuffer() {
        String value = getActivity().getPreferences(Activity.MODE_PRIVATE).getString(memoryKey, "0");
        calculator.setBuffer(Double.parseDouble(value));
    }

    private void saveExpression() {
        String expression =  UsefulFunctions.joinString("|", calculator.getExpression());
        Editor editor = getActivity().getPreferences(Activity.MODE_PRIVATE).edit();
        editor.putString(expressionKey, expression);
        editor.apply();
    }

    private void loadExpression() {
        String[] expressionParts = getActivity().getPreferences(Activity.MODE_PRIVATE).getString(expressionKey, "0").split("\\|");
        for (String expressionPart: expressionParts) {
            calculator.addExpressionPart(expressionPart);
        }
    }

    private void addCurrentExpressionToDB() {
        long time = System.currentTimeMillis();
        String[] expressionParts = new String[calculator.getExpression().size()];
        calculator.getExpression().toArray(expressionParts);
        String result = calculator.getStringResult();
        Expression expression = new Expression(0, time, expressionParts, result);
        CalculatorDB.getInstance().addExpression(expression);
    }

    private void printBD() {
        Log.d(CalculatorDB.tag, "expressions in DB:");
        for (Expression expression: CalculatorDB.getInstance().getExpressions()) {
            Log.d(CalculatorDB.tag, expression.toString());
        }

    }

}