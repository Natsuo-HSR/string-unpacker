package main.jp.nat;

import java.util.ArrayList;
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

    private static ArrayList<String> tokenize(String input) {
        ArrayList<String> answer = new ArrayList<>();
        String cur = null;
        int leftBracketCount = 0;
        int rightBracketCount = 0;
        int lastIndex;

        while (true) {
            if ((lastIndex = input.indexOf("]")) != -1) {
                // find first token
                cur = input.substring(0, lastIndex + 1);
                input = input.substring(lastIndex + 1);

                // check for an inner elements
                leftBracketCount = charCount(cur, '[');
                if (leftBracketCount > 1) {
                    do {
                        int nextIndex = input.indexOf("]") + 1;
                        cur += input.substring(0, nextIndex);
                        input = input.substring(nextIndex);
                        leftBracketCount = charCount(cur, '[');
                        rightBracketCount = charCount(cur, ']');
                    } while (leftBracketCount != rightBracketCount);
                }
                // add token to collection
                answer.add(cur);

            } else {
                if (input.length() > 0) {
                    answer.add(input);
                }
                break;
            }
        }

        return answer;
    }

    private static String transform(String input) {
        String transformed = "";

        Pattern innerReg = Pattern.compile("\\d+\\[\\w+\\]");
        Matcher m;
        while(true) {
            m = innerReg.matcher(input);
            if (m.find()) {
                transformed = multiply(m.group());
                input = input.replace(m.group(), transformed);
            } else {
                break;
            }
        }

        return input;
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
        StringBuffer answer = new StringBuffer("");

        if (!validate(input)) {
            return "Not valid";
        }

        ArrayList<String> tokens = tokenize(input);

        for (String s : tokens) {
            answer.append(transform(s));
        }

        return answer.toString();
    }

    public static void main(String[] args) {
        //System.out.println(unpack("4[xht]z"));
//        System.out.println(validate("4[xht]z"));
//        System.out.println(validate("3[xyz]4[xy]z"));
//        System.out.println(validate("2[3[x]y]4[xy]"));
//        System.out.println(validate("2[3[2[x]z]y]4[xy]"));
//
//        System.out.println(validate("3[33[x]!y]4[xy]"));
//        System.out.println(validate("a[xyz]4[xy]z"));
//        System.out.println(validate("3[xyz]4[xy]z]"));
    }
}
