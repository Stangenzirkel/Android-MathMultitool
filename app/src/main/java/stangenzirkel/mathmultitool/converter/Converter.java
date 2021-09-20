package stangenzirkel.mathmultitool.converter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import stangenzirkel.mathmultitool.ConverterFragment;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class Converter {
    private final static Converter instance = new Converter();
    private final List<String> numberChars = new ArrayList<>();
    private int input_ns = 10;
    private int result_ns = 10;
    final static private String tag = ConverterFragment.tag;

    private List<SolutionPart> solutionParts = new ArrayList<>();

    public static Converter getInstance() {
        return instance;
    }

    private Converter() {};

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

        int result;
        if (digits.contains(string)) {
            result = Integer.valueOf(string);
        } else if (lowercaseLetters.contains(string)) {
            result = lowercaseLetters.indexOf(string) + 10;
        } else if (capitalLetters.contains(string)) {
            result = capitalLetters.indexOf(string) + 10;
        } else {
            result = -1;
        }

        Log.d(tag, String.format("getDigitValue digit=%s, result=%d", string, result));
        return result;
    }

    private static String getDigitByValue(int value) {
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        if (value < 0 || value > 35) {
            return null;
        }

        String result = Character.toString(digits.charAt(value));

        Log.d(tag, String.format("getDigitByValue value=%d, result=%s", value, result));
        return result;
    }

    public boolean addString(String string) {
        for (String partOfString: string.split("")) {
            if (isDigit(partOfString)) {
                numberChars.add(partOfString.toUpperCase());
                Log.d(ConverterFragment.tag, "String added to converter input. String = ".concat(string));
            } else if ((string.equals(".") || string.equals(",")) && !numberChars.contains(".")) {
                numberChars.add(".");
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

    public String getResultString() {
        clearSolution();
        if (numberChars.size() == 0) {
            return "Result";
        }

        // method works with strings but input stored as ArrayList<String>
        try {
            // converting input to string
            String input = UsefulFunctions.joinString(numberChars);
            if (input.endsWith(".")) {
                input = input.replace(".", "");
            } else if (input.endsWith(".0")) {
                input = input.replace(".0", "");
            }

            String result;

//            if (getInputNumeralSystem() == getResultNumeralSystem()) {
//                result = input;
//            }

            // converting integer part
            // converting integer part to numeric system with base 10
            String inputIntegerPartString = input.split("\\.")[0];
            int inputIntegerPartIn10NS;
            if (getInputNumeralSystem() == 10) {
                inputIntegerPartIn10NS = Integer.parseInt(inputIntegerPartString);
            } else if (input.equals("0")) {
                inputIntegerPartIn10NS = 0;
            } else {
                SolutionPart solutionPart1 = new SolutionPart(String.format(Locale.getDefault(),"Converting integer part from numeric system with base %d to numeric system with base 10:",
                        getInputNumeralSystem()));
                inputIntegerPartIn10NS = 0;
                for (int i = 0; i < inputIntegerPartString.length(); i++) {
                    String substring = Character.toString(inputIntegerPartString.charAt(i));
                    int substringValue = getDigitValue(substring);
                    if (!isDigit(substring) || substringValue >= getInputNumeralSystem()) {
                        throw new RuntimeException("invalid char in expression: ".concat(substring));
                    }
                    solutionPart1.addLine(String.format(Locale.getDefault(), "%d + %d * %d ^ %d = %d",
                            inputIntegerPartIn10NS,
                            substringValue,
                            getInputNumeralSystem(),
                            inputIntegerPartString.length() - i - 1,
                            inputIntegerPartIn10NS + substringValue * (int) Math.pow(getInputNumeralSystem(), inputIntegerPartString.length() - i - 1)));

                    inputIntegerPartIn10NS += substringValue * Math.pow(getInputNumeralSystem(), inputIntegerPartString.length() - i - 1);
                }
                solutionPart1.addLine("");
                solutionPart1.addLine(String.format(Locale.getDefault(), "Result is %d", inputIntegerPartIn10NS));
                solutionParts.add(solutionPart1);
            }

            // converting integer part in numeric system with base 10 to result numeric system
            if (inputIntegerPartIn10NS == 0 ) {
                result = "0";
            } else if (getResultNumeralSystem() == 10) {
                result = Integer.toString(inputIntegerPartIn10NS);
            } else {
                SolutionPart solutionPart2 = new SolutionPart(String.format(Locale.getDefault(),"Converting integer part from numeric system with base 10 to numeric system with base %d:",
                        getResultNumeralSystem()));
                result = "";
                while ((inputIntegerPartIn10NS >= getResultNumeralSystem())) {
                    solutionPart2.addLine(String.format(Locale.getDefault(), "%d %% %d = %d (%s) result is (%s)%s",
                            inputIntegerPartIn10NS,
                            getResultNumeralSystem(),
                            inputIntegerPartIn10NS % getResultNumeralSystem(),
                            getDigitByValue(inputIntegerPartIn10NS % getResultNumeralSystem()),
                            getDigitByValue(inputIntegerPartIn10NS % getResultNumeralSystem()),
                            result));

                    result = getDigitByValue(inputIntegerPartIn10NS % getResultNumeralSystem()).concat(result);
                    inputIntegerPartIn10NS = inputIntegerPartIn10NS / getResultNumeralSystem();
                }
                result = getDigitByValue(inputIntegerPartIn10NS).concat(result);

                solutionPart2.addLine("");
                solutionPart2.addLine(String.format(Locale.getDefault(), "Result is %s", result));
                solutionParts.add(solutionPart2);
            }

            if (input.contains(".")) {
                // converting fractional part
                // converting fractional part to numeric system with base 10
                String inputFractionalPartString = input.split("\\.")[1];
                double inputFractionalPartIn10NS;

                if (getInputNumeralSystem() == 10) {
                    inputFractionalPartIn10NS = Double.parseDouble("0.".concat(inputFractionalPartString));
                } else {
                    solutionParts.add(new SolutionPart(String.format(Locale.getDefault(),"Converting fractional part from numeric system with base %d to numeric system with base 10:",
                            getInputNumeralSystem())));

                    inputFractionalPartIn10NS = 0;
                    for (int i = 0; i < inputFractionalPartString.length(); i++) {
                        String substring = Character.toString(inputFractionalPartString.charAt(i));
                        int substringValue = getDigitValue(substring);


                        if (!isDigit(substring) || substringValue >= getInputNumeralSystem()) {
                            throw new RuntimeException("invalid char in expression: ".concat(substring));
                        }
                        inputFractionalPartIn10NS += substringValue * Math.pow(getInputNumeralSystem(), -i - 1);
                    }
                }

                // converting fractional part in numeric system with base 10 to result numeric system
                if (getResultNumeralSystem() == 10) {
                    result = result.concat(Double.toString(inputFractionalPartIn10NS).substring(1));
                } else {
                    solutionParts.add(new SolutionPart(String.format(Locale.getDefault(),"Converting fractional part from numeric system with base 10 to numeric system with base %d:",
                            getResultNumeralSystem())));

                    result = result.concat(".");
                    for (int i = 0; i < 6; i++) {
                        Log.d(tag, Double.toString(inputFractionalPartIn10NS * getResultNumeralSystem()));
                        result = result.concat(Integer.toString((int) (inputFractionalPartIn10NS * getResultNumeralSystem())));
                        inputFractionalPartIn10NS = (inputFractionalPartIn10NS * getResultNumeralSystem()) % 1;

                    }
                }
            }

            Log.d(tag, String.format("Number converted input=%s, result=%s, input numeric system=%d, result numeric system=%d",
                    input,
                    result,
                    getInputNumeralSystem(),
                    getResultNumeralSystem()));
            return result;

        } catch (NumberFormatException e) {
            Log.d(tag, "Error while convert");
            Log.d(tag, Log.getStackTraceString(e));
            return "Number is too large";
        } catch (Throwable e) {
            Log.d(tag, "Error while convert");
            Log.d(tag, Log.getStackTraceString(e));
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

    public List<SolutionPart> getSolution() {
        return solutionParts;
    }

    public void clearSolution() {
        solutionParts.clear();
    }
}
