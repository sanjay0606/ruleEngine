package com.example.ruleEngine.ruleEngine.Entity;

public class ASTNode {

    private String type;
    private String value;
    private ASTNode left;
    private ASTNode right;


    public ASTNode() {
    }

    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ASTNode getLeft() {
        return left;
    }

    public void setLeft(ASTNode left) {
        this.left = left;
    }

    public ASTNode getRight() {
        return right;
    }

    public void setRight(ASTNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
