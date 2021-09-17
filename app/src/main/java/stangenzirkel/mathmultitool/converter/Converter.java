package stangenzirkel.mathmultitool.converter;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    private List<String> numberChars = new ArrayList<>();

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
            } else if (string.equals(",") && !numberChars.contains(",")) {
                numberChars.add(partOfString);
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

    public String getStringResult() {
        if (numberChars.size() == 0) {
            return "Result";
        }

        try {
            return "0";
        } catch (Throwable e) {
            return "Error";
        }
    }
}
