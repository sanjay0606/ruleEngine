package com.example.ruleEngine.ruleEngine.Entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "rules")

public class Rule {
    @Id
    private String id;
    private String stringExpression;
    private  ASTNode node;

    public String getStringExpression() {
        return stringExpression;
    }

    public void setStringExpression(String stringExpression) {
        this.stringExpression = stringExpression;
    }

    public ASTNode getNode() {
        return node;
    }

    public void setNode(ASTNode node) {
        this.node = node;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
