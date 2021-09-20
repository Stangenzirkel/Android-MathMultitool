package stangenzirkel.mathmultitool.usefulfunctions;

public class UsefulFunctions {
    static public String joinString(Iterable<String> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object addable: iterable) {
            stringBuilder.append(addable);
        }
        return stringBuilder.toString();
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
