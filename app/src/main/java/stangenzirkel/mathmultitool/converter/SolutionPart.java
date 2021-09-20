package stangenzirkel.mathmultitool.converter;

import java.util.Arrays;

public class SolutionPart {
    private String header;
    private String[] text = new String[0];

    public SolutionPart(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public void addLine(String line) {
        text = Arrays.copyOf(text, text.length + 1);
        text[text.length - 1] = line;
    }


}
