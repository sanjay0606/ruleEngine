package com.example.ruleEngine.ruleEngine.methods;

import com.example.ruleEngine.ruleEngine.services.Token;

import java.util.ArrayList;
import java.util.List;

public class ConvertToTokens {

    public static List<Token> tokenize(String ruleString) {

        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < ruleString.length(); i++) {
            char ch = ruleString.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            } else if (ch == '(') {
                tokens.add(new Token("(", Token.TokenType.LEFT_PARENTHESIS));
            } else if (ch == ')') {
                tokens.add(new Token(")", Token.TokenType.RIGHT_PARENTHESIS));
            } else if (ruleString.startsWith("AND", i)) {
                tokens.add(new Token("AND", Token.TokenType.OPERATOR));
                i += 2; // Skip the next two characters
            } else if (ruleString.startsWith("OR", i)) {
                tokens.add(new Token("OR", Token.TokenType.OPERATOR));
                i += 1; // Skip the next character
            } else if (ruleString.startsWith(">=", i)) {
                tokens.add(new Token(">=", Token.TokenType.OPERATOR));
                i += 1; // Skip the next character
            } else if (ruleString.startsWith("<=", i)) {
                tokens.add(new Token("<=", Token.TokenType.OPERATOR));
                i += 1; // Skip the next character
            } else if (ruleString.startsWith(">", i) || ruleString.startsWith("<", i) || ruleString.startsWith("=", i)) {
                tokens.add(new Token(String.valueOf(ch), Token.TokenType.OPERATOR));
            } else if (ch == '\'') {
                currentToken.append(ch);
                i++;
                while (i < ruleString.length() && ruleString.charAt(i) != '\'') {
                    currentToken.append(ruleString.charAt(i));
                    i++;
                }
                if (i < ruleString.length()) {
                    currentToken.append('\'');
                }

                tokens.add(new Token(currentToken.toString().substring(1, currentToken.length() - 1), Token.TokenType.OPERAND));
                currentToken.setLength(0);
            } else {
                currentToken.append(ch);
                while (i + 1 < ruleString.length() && !Character.isWhitespace(ruleString.charAt(i + 1))
                        && ruleString.charAt(i + 1) != '(' && ruleString.charAt(i + 1) != ')'
                        && !ruleString.startsWith("AND", i + 1) && !ruleString.startsWith("OR", i + 1)
                        && !isOperatorStart(ruleString.charAt(i + 1))) {
                    currentToken.append(ruleString.charAt(++i));
                }
                tokens.add(new Token(currentToken.toString(), Token.TokenType.OPERAND));
                currentToken.setLength(0);
            }
        }

        return tokens;

    }

    private static boolean isOperatorStart(char ch) {
        return ch == '>' || ch == '<' || ch == '=';
    }
}
