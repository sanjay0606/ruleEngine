package com.example.ruleEngine.ruleEngine.DTO;

import com.example.ruleEngine.ruleEngine.Entity.ASTNode;
import com.example.ruleEngine.ruleEngine.Entity.User;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class EvaluationRequest {
    private ASTNode node;

    private User userAttributes;

    public User getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(User userAttributes) {
        this.userAttributes = userAttributes;
    }

    public ASTNode getNode() {
        return node;
    }

    public void setNode(ASTNode node) {
        this.node = node;
    }
}
