package stangenzirkel.mathmultitool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import stangenzirkel.mathmultitool.converter.Converter;

public class ConverterFragment extends Fragment implements View.OnClickListener {
    private Converter converter = Converter.getInstance();
    private int mod = 0; // 0 - converter; 1 - input ns; 2 - result ns
    public static final String tag = "ConverterFragmentTag";
    private final View.OnClickListener onClickListener = v -> {
        Button button = (Button) v;
        int system = Integer.parseInt((String) button.getText());
        Log.d(tag, "Click on numeric system button");
        Log.d(tag, "Mod = ".concat(Integer.toString(mod)));
        // Log.d(tag, "System = ".concat(Integer.toString(system)));
        Log.d(tag, "Current system = ".concat(Integer.toString(system)));

        if (mod == 1) {
            Log.d(tag, "Current system = ".concat(Integer.toString(converter.getInputNumeralSystem())));
            converter.setInputNumeralSystem(system);
            Log.d(tag, "New system = ".concat(Integer.toString(converter.getInputNumeralSystem())));
            Button button1 = getView().findViewById(R.id.btn_numeral_system_input);
            button1.setText(Integer.toString(converter.getInputNumeralSystem()));
            disableButtons();
        } else if (mod == 2) {
            Log.d(tag, "Current system = ".concat(Integer.toString(converter.getResultNumeralSystem())));
            converter.setResultNumeralSystem(system);
            Log.d(tag, "New system = ".concat(Integer.toString(converter.getResultNumeralSystem())));
            Button button1 = getView().findViewById(R.id.btn_numeral_system_result);
            button1.setText(Integer.toString(converter.getResultNumeralSystem()));
        }


        TextView tv = getView().findViewById(R.id.tv_converter_input);
        tv.setText(converter.getInputString());

        tv = getView().findViewById(R.id.tv_converter_result);
        tv.setText(converter.getResultString());

        switchToMainKeyboard();
    };

    private void openNumericKeyboard() {
        getView().findViewById(R.id.converter_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.numeral_keyboard).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.textView3).setVisibility(View.VISIBLE);
    }

    private void switchToMainKeyboard() {
        getView().findViewById(R.id.converter_keyboard).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.numeral_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.textView3).setVisibility(View.GONE);
    }

    private void closeMainKeyboard() {
        getView().findViewById(R.id.converter_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.numeral_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.textView3).setVisibility(View.GONE);
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        // closeMainKeyboard();
    }

    private void disableButtons() {
        updateFragment(getView());
    }

    private void updateFragment (View root) {
        Button buttonInput = root.findViewById(R.id.btn_numeral_system_input);
        buttonInput.setText(Integer.toString(converter.getInputNumeralSystem()));

        Button buttonResult = root.findViewById(R.id.btn_numeral_system_result);
        buttonResult.setText(Integer.toString(converter.getResultNumeralSystem()));

        List<Button> buttons = new ArrayList<>();
        buttons.add(root.findViewById(R.id.btn_0));
        buttons.add(root.findViewById(R.id.btn_1));
        buttons.add(root.findViewById(R.id.btn_2));
        buttons.add(root.findViewById(R.id.btn_3));
        buttons.add(root.findViewById(R.id.btn_4));
        buttons.add(root.findViewById(R.id.btn_5));
        buttons.add(root.findViewById(R.id.btn_6));
        buttons.add(root.findViewById(R.id.btn_7));
        buttons.add(root.findViewById(R.id.btn_8));
        buttons.add(root.findViewById(R.id.btn_9));
        buttons.add(root.findViewById(R.id.btn_a));
        buttons.add(root.findViewById(R.id.btn_b));
        buttons.add(root.findViewById(R.id.btn_c));
        buttons.add(root.findViewById(R.id.btn_d));
        buttons.add(root.findViewById(R.id.btn_e));
        buttons.add(root.findViewById(R.id.btn_f));

        for (int i = 0; i < 16; i++) {
            buttons.get(i).setEnabled(true);
        }
        for (int i = 15; i >= converter.getInputNumeralSystem(); i--) {
            buttons.get(i).setEnabled(false);
        }
    }

    // TODO
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        // switchToMainKeyboard();
    }


    public ConverterFragment() {
        // Required empty public constructor
    }

    public static ConverterFragment newInstance() {
        ConverterFragment fragment = new ConverterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_converter, container, false);

        root.findViewById(R.id.btn_solution).setOnClickListener(this);
        root.findViewById(R.id.btn_numeral_system_input).setOnClickListener(this);
        root.findViewById(R.id.btn_numeral_system_result).setOnClickListener(this);
        root.findViewById(R.id.btn_keyboard).setOnClickListener(this);

        root.findViewById(R.id.btn_1).setOnClickListener(this);
        root.findViewById(R.id.btn_2).setOnClickListener(this);
        root.findViewById(R.id.btn_3).setOnClickListener(this);
        root.findViewById(R.id.btn_4).setOnClickListener(this);
        root.findViewById(R.id.btn_5).setOnClickListener(this);
        root.findViewById(R.id.btn_6).setOnClickListener(this);
        root.findViewById(R.id.btn_7).setOnClickListener(this);
        root.findViewById(R.id.btn_8).setOnClickListener(this);
        root.findViewById(R.id.btn_9).setOnClickListener(this);
        root.findViewById(R.id.btn_0).setOnClickListener(this);

        root.findViewById(R.id.btn_a).setOnClickListener(this);
        root.findViewById(R.id.btn_b).setOnClickListener(this);
        root.findViewById(R.id.btn_c).setOnClickListener(this);
        root.findViewById(R.id.btn_d).setOnClickListener(this);
        root.findViewById(R.id.btn_e).setOnClickListener(this);
        root.findViewById(R.id.btn_f).setOnClickListener(this);

        root.findViewById(R.id.btn_ca).setOnClickListener(this);
        root.findViewById(R.id.btn_del).setOnClickListener(this);
        root.findViewById(R.id.btn_dot).setOnClickListener(this);

        root.findViewById(R.id.textView3).setVisibility(View.GONE);

        root.findViewById(R.id.btn_ns2).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns3).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns4).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns5).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns6).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns7).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns8).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns9).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns10).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns11).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns12).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns13).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns14).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns15).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns16).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns17).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns18).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns19).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns20).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns21).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns22).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns23).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns24).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns25).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns26).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns27).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns28).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns29).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns31).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns32).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns33).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns34).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns35).setOnClickListener(onClickListener);
        root.findViewById(R.id.btn_ns36).setOnClickListener(onClickListener);

        root.findViewById(R.id.btn_cancel).setOnClickListener(this);

        TextView tv = root.findViewById(R.id.tv_converter_input);
        tv.setText(converter.getInputString());

        tv = root.findViewById(R.id.tv_converter_result);
        tv.setText(converter.getResultString());

        root.findViewById(R.id.converter_keyboard).setVisibility(View.VISIBLE);
        updateFragment(root);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_numeral_system_input:
                mod = 1;
                openNumericKeyboard();
                break;

            case R.id.btn_numeral_system_result:
                mod = 2;
                openNumericKeyboard();
                break;

            case R.id.btn_cancel:
                mod = 0;
                switchToMainKeyboard();
                break;

            case R.id.btn_keyboard:
                showKeyboard();
                break;

            case R.id.btn_0:
                converter.addString("0");
                break;

            case R.id.btn_1:
                converter.addString("1");
                break;

            case R.id.btn_2:
                converter.addString("2");
                break;

            case R.id.btn_3:
                converter.addString("3");
                break;

            case R.id.btn_4:
                converter.addString("4");
                break;

            case R.id.btn_5:
                converter.addString("5");
                break;

            case R.id.btn_6:
                converter.addString("6");
                break;

            case R.id.btn_7:
                converter.addString("7");
                break;

            case R.id.btn_8:
                converter.addString("8");
                break;

            case R.id.btn_9:
                converter.addString("9");
                break;

            case R.id.btn_a:
                converter.addString("a");
                break;

            case R.id.btn_b:
                converter.addString("b");
                break;

            case R.id.btn_c:
                converter.addString("c");
                break;

            case R.id.btn_d:
                converter.addString("d");
                break;

            case R.id.btn_e:
                converter.addString("e");
                break;

            case R.id.btn_f:
                converter.addString("f");
                break;

            case R.id.btn_dot:
                converter.addString(",");
                break;

            case R.id.btn_del:
                converter.clearLast();
                break;

            case R.id.btn_ca:
                converter.clearAll();
                break;
        }
        TextView tv = getView().findViewById(R.id.tv_converter_input);
        tv.setText(converter.getInputString());

        tv = getView().findViewById(R.id.tv_converter_result);
        tv.setText(converter.getResultString());
    }

    public boolean onKeyboardKey(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_DEL) {
            converter.clearLast();
        } else if (keyCode==KeyEvent.KEYCODE_ENTER) {
            hideKeyboard();
        }
        Converter.getInstance().addString(String.valueOf((char) event.getUnicodeChar()));
        TextView tv = getView().findViewById(R.id.tv_converter_input);
        tv.setText(converter.getInputString());

        tv = getView().findViewById(R.id.tv_converter_result);
        tv.setText(converter.getResultString());
        return false;
    }
}