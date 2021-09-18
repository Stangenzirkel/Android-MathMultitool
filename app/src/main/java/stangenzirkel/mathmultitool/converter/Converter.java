package stangenzirkel.mathmultitool.converter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import stangenzirkel.mathmultitool.ConverterFragment;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class Converter {
    private final static Converter instance = new Converter();
    private final List<String> numberChars = new ArrayList<>();
    private int input_ns = 10;
    private int result_ns = 10;

    public static Converter getInstance() {
        return instance;
    }

    private Converter() {

    };

    private static boolean isDigit(String string) {
        return getDigitValue(string) != -1;
    }

    private static int getDigitValue(String string) {
        String digits = "1234567890";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (string.length() != 1) {
            return -1;
        }

        if (digits.contains(string)) {
            return Integer.valueOf(string);
        } else if (lowercaseLetters.contains(string)) {
            return lowercaseLetters.indexOf(string) + 10;
        } else if (capitalLetters.contains(string)) {
            return capitalLetters.indexOf(string) + 10;
        } else {
            return -1;
        }
    }

    public boolean addString(String string) {
        for (String partOfString: string.split("")) {
            if (isDigit(partOfString)) {
                numberChars.add(partOfString);
                Log.d(ConverterFragment.tag, "String added to converter input. String = ".concat(string));
            } else if (string.equals(",") && !numberChars.contains(",")) {
                numberChars.add(partOfString);
                Log.d(ConverterFragment.tag, "String added to converter input. String = ".concat(string));
            } else {
                return false;
            }
        }

        return true;
    }

    public void clearAll() {
        numberChars.clear();
    }

    public void clearLast() {
        if (numberChars.size() != 0) {
            numberChars.remove(numberChars.size() - 1);
        }
    }

    public String getInputString() {
        if (numberChars.size() == 0) {
            return "Enter here";
        }
        return UsefulFunctions.joinString(numberChars);
    }

    public String getStringResult() {
        if (numberChars.size() == 0) {
            return "Result";
        }

        try {
            // TODO
            return "0";
        } catch (Throwable e) {
            return "Error";
        }
    }

    public int getResultNumeralSystem() {
        return result_ns;
    }

    public void setResultNumeralSystem(int value) {
        if (1 < value && value < 37) {
            this.result_ns = value;
        }
    }

    public int getInputNumeralSystem() {
        return input_ns;
    }

    public void setInputNumeralSystem(int value) {
        if (1 < value && value < 37) {
            this.input_ns = value;
        }
    }
}
