package com.example.ruleEngine.ruleEngine.services;

import java.util.ArrayList;
import java.util.List;

public class Token {


    public enum TokenType {
        OPERATOR,
        OPERAND,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS
    }
    private TokenType type;
    private String value;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }





}


