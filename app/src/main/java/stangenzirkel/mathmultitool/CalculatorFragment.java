package stangenzirkel.mathmultitool;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import stangenzirkel.mathmultitool.calculator.Calculator;

public class CalculatorFragment extends Fragment implements View.OnClickListener {
    private Calculator calculator = new Calculator();
    private String tag = "CalculatorFragmentTag";

    public CalculatorFragment() {

    }

    public static CalculatorFragment newInstance() {
        CalculatorFragment fragment = new CalculatorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
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
        root.findViewById(R.id.btn_rad_deg).setOnClickListener(this);
        root.findViewById(R.id.btn_mc).setOnClickListener(this);
        root.findViewById(R.id.btn_mplus).setOnClickListener(this);
        root.findViewById(R.id.btn_mminus).setOnClickListener(this);
        root.findViewById(R.id.btn_mr).setOnClickListener(this);
        root.findViewById(R.id.btn_lscope).setOnClickListener(this);
        root.findViewById(R.id.btn_rscope).setOnClickListener(this);
        root.findViewById(R.id.btn_pi).setOnClickListener(this);
        root.findViewById(R.id.btn_e).setOnClickListener(this);
        root.findViewById(R.id.btn_plus).setOnClickListener(this);
        root.findViewById(R.id.btn_minus).setOnClickListener(this);
        root.findViewById(R.id.btn_multiplication).setOnClickListener(this);
        root.findViewById(R.id.btn_division).setOnClickListener(this);
        root.findViewById(R.id.btn_exponentiation).setOnClickListener(this);
        root.findViewById(R.id.btn_root).setOnClickListener(this);
        root.findViewById(R.id.btn_dot).setOnClickListener(this);
        // root.findViewById(R.id.btn_percent).setOnClickListener(this);

        TextView tv_calculator_expression = (TextView) root.findViewById(R.id.tv_calculator_expression);
        tv_calculator_expression.setText(calculator.getString());

        TextView tv_calculator_result = (TextView) root.findViewById(R.id.tv_calculator_result);
        tv_calculator_result.setText(calculator.getStringResult());
        return root;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        Log.d(tag, "Click on ".concat((String) button.getText()));
        switch (v.getId()) {
            case R.id.btn_1:
                calculator.addExpressionPart("1");
                break;

            case R.id.btn_2:
                calculator.addExpressionPart("2");
                break;

            case R.id.btn_3:
                calculator.addExpressionPart("3");
                break;

            case R.id.btn_4:
                calculator.addExpressionPart("4");
                break;

            case R.id.btn_5:
                calculator.addExpressionPart("5");
                break;

            case R.id.btn_6:
                calculator.addExpressionPart("6");
                break;

            case R.id.btn_7:
                calculator.addExpressionPart("7");
                break;

            case R.id.btn_8:
                calculator.addExpressionPart("8");
                break;

            case R.id.btn_9:
                calculator.addExpressionPart("9");
                break;

            case R.id.btn_0:
                calculator.addExpressionPart("0");
                break;

            case R.id.btn_result:
                String result = calculator.getStringResult();
                calculator.clear();
                for (char elem: result.toCharArray()) {
                    calculator.addExpressionPart(String.valueOf(elem));
                }
                break;

            case R.id.btn_c:
                calculator.clear();
                break;

            case R.id.btn_del:
                calculator.clearLast();
                break;

            case R.id.btn_lscope:
                calculator.addExpressionPart("(");
                break;

            case R.id.btn_rscope:
                calculator.addExpressionPart(")");
                break;

            case R.id.btn_plus:
                calculator.addExpressionPart("+");
                break;

            case R.id.btn_minus:
                calculator.addExpressionPart("-");
                break;

            case R.id.btn_multiplication:
                calculator.addExpressionPart("*");
                break;

            case R.id.btn_division:
                calculator.addExpressionPart("/");
                break;

            case R.id.btn_exponentiation:
                calculator.addExpressionPart("^");
                break;

            case R.id.btn_root:
                calculator.addExpressionPart("root");
                break;

            case R.id.btn_percent:
                calculator.addExpressionPart("%");
                break;

            case R.id.btn_pi:
                calculator.addExpressionPart("pi");
                break;

            case R.id.btn_e:
                calculator.addExpressionPart("e");
                break;

            case R.id.btn_mc:
                break;

            case R.id.btn_mplus:
                break;

            case R.id.btn_mminus:
                break;

            case R.id.btn_mr:
                break;

            case R.id.btn_rad_deg:
                calculator.setRadianMod(!calculator.isRadianMod());

                Button btn = (Button) getView().findViewById(R.id.btn_rad_deg);
                if (calculator.isRadianMod()) {
                    btn.setText("deg");
                } else {
                    btn.setText("rad");
                }
                break;

            case R.id.btn_dot:
                calculator.addExpressionPart(",");
                break;
        }

        TextView tv_calculator_expression = (TextView) getView().findViewById(R.id.tv_calculator_expression);
        tv_calculator_expression.setText(calculator.getString());

        TextView tv_calculator_result = (TextView) getView().findViewById(R.id.tv_calculator_result);
        tv_calculator_result.setText(calculator.getStringResult());
    }
}