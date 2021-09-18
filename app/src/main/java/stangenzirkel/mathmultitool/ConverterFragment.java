package stangenzirkel.mathmultitool;

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

import stangenzirkel.mathmultitool.converter.Converter;

import static androidx.core.content.ContextCompat.getSystemService;

public class ConverterFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {
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
        } else if (mod == 2) {
            Log.d(tag, "Current system = ".concat(Integer.toString(converter.getResultNumeralSystem())));
            converter.setResultNumeralSystem(system);
            Log.d(tag, "New system = ".concat(Integer.toString(converter.getResultNumeralSystem())));
            Button button1 = getView().findViewById(R.id.btn_numeral_system_result);
            button.setText(Integer.toString(converter.getResultNumeralSystem()));
        }


        TextView tv = getView().findViewById(R.id.tv_converter_input);
        tv.setText(converter.getInputString());

        tv = getView().findViewById(R.id.tv_converter_result);
        tv.setText(converter.getStringResult());

        closeNumericKeyboard();
    };

    private void openNumericKeyboard() {
        getView().findViewById(R.id.converter_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.numeral_keyboard).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.textView3).setVisibility(View.VISIBLE);
    }

    private void closeNumericKeyboard() {
        getView().findViewById(R.id.converter_keyboard).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.numeral_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.textView3).setVisibility(View.GONE);
    }

    private void openKeyboard() {
        closeNumericKeyboard();
    }

    private void closeKeyboard() {
        getView().findViewById(R.id.converter_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.numeral_keyboard).setVisibility(View.GONE);
        getView().findViewById(R.id.textView3).setVisibility(View.GONE);
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
        tv.setText(converter.getStringResult());

        root.findViewById(R.id.converter_keyboard).setVisibility(View.VISIBLE);
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
                closeNumericKeyboard();
                break;

            case R.id.btn_keyboard:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                break;
        }
        TextView tv = getView().findViewById(R.id.tv_converter_input);
        tv.setText(converter.getInputString());

        tv = getView().findViewById(R.id.tv_converter_result);
        tv.setText(converter.getStringResult());
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        Log.d(tag, "Key clicked. Code = ".concat(String.valueOf(keyCode)));
        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(tag, "Key clicked. Code = ".concat(String.valueOf(keyCode)));
        return false;
    }
}