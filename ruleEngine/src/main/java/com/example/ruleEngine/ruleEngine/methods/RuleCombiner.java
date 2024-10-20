package com.example.ruleEngine.ruleEngine.methods;

import com.example.ruleEngine.ruleEngine.Entity.ASTNode;
import com.example.ruleEngine.ruleEngine.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.*;


@Component
public class RuleCombiner {

    @Autowired
    private RuleService ruleService;

    public ASTNode rulescombine(List<String> rules,String operator){

      String combinedExpression=ruleService.parseASTToString(rules,operator);

        return ruleService.parseRuleString(combinedExpression.toString());


    }

}
