/*Names: Ishaan Aggarwal, Luke Blaydes
* Project Phase 2.1: parser for expressions
*/

import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

public class Scanner {
    ArrayList<Token> tokens;
    private boolean isError = false;
    private final static String id = "([a-z]|[A-Z])([a-z]|[A-Z]|[0-9])*";
    private final static String number = "\\d+";
    private final static String symbol = "([+]|[-]|[*]|/|[(]|[)]|:=|;)";
    private final static String keyword = "(if|then|else|endif|while|do|endwhile|skip)";

    int i;
    public Scanner(String input) {
        tokens = new ArrayList<Token>();
        String lastToken = "";
        String token = "";
        int length = input.length();
        int count;
        i = 0;
        for (count = 0; count < length && input != null; count++) {
            token = lastToken;
            token += input.charAt(count);
            if (!token.matches(id) && !token.matches(number) && !token.matches(symbol) || (input.charAt(count) == ' ' || input.charAt(count) == '\t' || input.charAt(count) == '\n')) {
                if (!lastToken.contains(" "))
                    tokens.add(match(lastToken));
                lastToken = "" + input.charAt(count);
                // System.out.println("Last token : " + lastToken);
            } else {
                lastToken = token;
            }
        }
        // System.out.println(lastToken);
        tokens.add(match(lastToken));
    }

    /*
     * helper function used for matching the current token being parsed
     */
    public static Token match(String lastToken) {
        Token tok = new Token();

        if (lastToken.matches(keyword)) {
            tok.setToken(lastToken);
            tok.setType("KEYWORD");

        } else if (lastToken.matches(id)) {
            tok.setToken(lastToken);
            tok.setType("IDENTIFIER");
        } else if (lastToken.matches(number)) {
            tok.setToken(lastToken);
            tok.setType("NUMBER");
        } else if (lastToken.matches(symbol)) {
            tok.setToken(lastToken);
            tok.setType("SYMBOL");
        } else {

            tok.setToken(lastToken);
            tok.setType("ERROR READING \"" + lastToken + "\"");
        }
        return tok;
    }

    public Token next() {
        i++;
        return tokens.get(i);
    }

    public boolean hasNext() {

        if (i + 1 < tokens.size()) {
            return true;
        } else
            return false;
    }

    public ArrayList<Token> recv() {
        return tokens;
    }
}