package stangenzirkel.mathmultitool.usefulfunctions;

public class UsefulFunctions {
    static public String joinString(Iterable<String> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object addable: iterable) {
            stringBuilder.append(addable);
        }
        return stringBuilder.toString();
    }
}
