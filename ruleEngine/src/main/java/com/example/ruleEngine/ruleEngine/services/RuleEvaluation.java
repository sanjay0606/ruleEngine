package com.example.ruleEngine.ruleEngine.services;

import com.example.ruleEngine.ruleEngine.Entity.ASTNode;
import com.example.ruleEngine.ruleEngine.Entity.User;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RuleEvaluation {

    public boolean evaluateRule(ASTNode ast, User user) {
        if (ast.getType().equals("operator")) {
            String operator = ast.getValue();
            boolean leftEval = evaluateRule(ast.getLeft(), user);
            boolean rightEval = evaluateRule(ast.getRight(), user);

            if (operator.equals("AND")) {
                return leftEval && rightEval;
            } else if (operator.equals("OR")) {
                return leftEval || rightEval;
            }
        } else if (ast.getType().equals("operand")) {
            return evaluateCondition(ast.getValue(), user);
        }
        return false;
    }

    private boolean evaluateCondition(String condition, User user) {
        String[] parts = condition.split(" ");
        String attribute = parts[0];  // e.g., "age"
        String operator = parts[1];   // e.g., ">"
        String value = parts[2];      // e.g., "30" or "'Sales'"

        // Handle different attributes
        switch (attribute) {
            case "age":
                return compare(user.getAge(), Integer.parseInt(value), operator);
            case "department":
                return compare(user.getDepartment(), value.replace("'", ""), operator);
            case "income":
                return compare(user.getIncome(), Integer.parseInt(value), operator);
            case "spend":
                return compare(user.getSpend(), Integer.parseInt(value), operator);
            default:
                return false;
        }
    }

    private boolean compare(int userValue, int conditionValue, String operator) {
        switch (operator) {
            case ">":
                return userValue > conditionValue;
            case "<":
                return userValue < conditionValue;
            case "=":
                return userValue == conditionValue;
            default:
                return false;
        }
    }

    // Helper method for string comparisons (like department = 'Sales')
    private boolean compare(String userValue, String conditionValue, String operator) {
        return operator.equals("=") && userValue.equals(conditionValue);
    }

}
