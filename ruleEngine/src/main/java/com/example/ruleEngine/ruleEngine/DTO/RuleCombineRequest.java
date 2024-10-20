package com.example.ruleEngine.ruleEngine.DTO;

import java.util.List;

public class RuleCombineRequest {

    private String combineoperator;

    public String getCombineoperator() {
        return combineoperator;
    }

    public void setCombineoperator(String combineoperator) {
        this.combineoperator = combineoperator;
    }

    private List<String> rules;

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
