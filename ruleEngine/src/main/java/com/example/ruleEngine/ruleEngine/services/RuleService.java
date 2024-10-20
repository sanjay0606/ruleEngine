package com.example.ruleEngine.ruleEngine.services;

import com.example.ruleEngine.ruleEngine.Entity.ASTNode;
import com.example.ruleEngine.ruleEngine.Entity.Rule;
import com.example.ruleEngine.ruleEngine.Repositry.RuleRepositry;
import com.example.ruleEngine.ruleEngine.methods.ConvertToTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;


@Service
public class RuleService {


    @Autowired
    private RuleRepositry ruleRepositry;

    public ASTNode parseRuleString(String ruleString){

        List<Token> tokens = ConvertToTokens.tokenize(ruleString.trim());

        return parseExpression(tokens);
    }

    private ASTNode parseExpression(List<Token> tokens){

        Stack<ASTNode> operandStack = new Stack<>();
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case OPERAND:
                    operandStack.push(new ASTNode("operand", token.getValue()));
                    break;
                case OPERATOR:
                    while (!operatorStack.isEmpty() && precedence(token) <= precedence(operatorStack.peek())) {
                        operandStack.push(applyOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
                    }
                    operatorStack.push(token);
                    break;
                case LEFT_PARENTHESIS:
                    operatorStack.push(token);
                    break;
                case RIGHT_PARENTHESIS:
                    while (!operatorStack.isEmpty() && operatorStack.peek().getType() != Token.TokenType.LEFT_PARENTHESIS) {
                        operandStack.push(applyOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
                    }
                    operatorStack.pop();
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            operandStack.push(applyOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
        }

        return operandStack.pop();

    }

    private int precedence(Token token) {
        switch (token.getValue()) {
            case "AND":
                return 1;
            case "OR":
                return 0;
            case ">":
            case "<":
            case "=":
            case ">=":
            case "<=":
                return 2;
            default:
                return -1;
        }
    }

    private ASTNode applyOperator(Token operator, ASTNode right, ASTNode left) {
        ASTNode node = new ASTNode("operator", operator.getValue());
        node.setLeft(left);
        node.setRight(right);
        return node;
    }

    public Rule createRule(String ruleString, ASTNode ast) {
        Rule rule = new Rule();
        rule.setStringExpression(ruleString);
        rule.setNode(ast);
        return ruleRepositry.save(rule);
    }

    public String parseASTToString(List<String> rules,String operator) {
        if (rules == null || rules.isEmpty()) {
            return null;
        }
        StringBuilder combinedExpression = new StringBuilder();




        combinedExpression.append("(");

        for (int i = 0; i < rules.size(); i++) {
            combinedExpression.append(rules.get(i));

            if (i < rules.size() - 1) {
                combinedExpression.append(operator);
            }
        }

        combinedExpression.append(")");
        return combinedExpression.toString();
    }

}
