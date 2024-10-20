package com.example.ruleEngine.ruleEngine.DTO;


import org.springframework.stereotype.Component;

@Component
public class RuleRequest {

    private String ruleString;

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }
}
