package main.jp.nat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringGenerator {

    private static int charCount(String seq, char toFind) {
        int count = 0;

        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == toFind)
                count++;
        }

        return count;
    }

    private static boolean validate(String input) {
        boolean isValid = true;

        Pattern characterBeforeBracket = Pattern.compile("[a-zA-Z]\\[");
        Pattern wrongCharacters = Pattern.compile("[^a-zA-z0-9]");

        Matcher m1 = characterBeforeBracket.matcher(input);
        Matcher m2 = wrongCharacters.matcher(input);

        int leftBracketsNum = charCount(input, '[');
        int rightBracketsNum = charCount(input, ']');

        if (m1.find() || m2.find() || leftBracketsNum != rightBracketsNum) {
            isValid = false;
        }

        return isValid;
    }

    private static String multiply(String input) {
        String[] list = input.split("\\[");

        int limit = Integer.parseInt(list[0]);
        String toMultiply = list[1].substring(0, list[1].length() - 1);

        StringBuffer result = new StringBuffer("");

        for (int i = 0; i < limit; i++) {
            result.append(toMultiply);
        }

        return result.toString();
    }

    public static String unpack(String input) {

        if (!validate(input)) {
            return "Not valid";
        }

        Pattern innerReg = Pattern.compile("\\d+\\[\\w+\\]");
        Matcher m;
        while((m = innerReg.matcher(input)).find()) {
            input = input.replace(m.group(), multiply(m.group()));
        }

        return input;
    }

    public static void main(String[] args) {
    }
}
