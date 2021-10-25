package stangenzirkel.mathmultitool.usefulfunctions;

import android.util.Log;

import java.util.Arrays;

public class UsefulFunctions {
    static public String joinString(String delimiter, String[] strings) {
        Log.d("UsefulFunctionsTag", joinString(delimiter, Arrays.asList(strings)));
        return joinString(delimiter, Arrays.asList(strings));
    }
    static public String joinString(String[] strings) {
        return joinString("", strings);
    }

    static public String joinString(Iterable<String> iterable) {
        return joinString("", iterable);
    }

    static public String joinString(String delimiter, Iterable<String> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object addable: iterable) {
            stringBuilder.append(addable);
            stringBuilder.append(delimiter);
        }
        String result = stringBuilder.toString();
        if (result.length() != 0) {
            result = result.substring(0, stringBuilder.length() - delimiter.length());
        }
        return result;
    }
}
