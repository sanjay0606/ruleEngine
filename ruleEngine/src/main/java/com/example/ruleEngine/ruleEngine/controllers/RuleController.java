package com.example.ruleEngine.ruleEngine.controllers;


import com.example.ruleEngine.ruleEngine.DTO.EvaluationRequest;
import com.example.ruleEngine.ruleEngine.DTO.RuleCombineRequest;
import com.example.ruleEngine.ruleEngine.DTO.RuleRequest;
import com.example.ruleEngine.ruleEngine.Entity.ASTNode;
import com.example.ruleEngine.ruleEngine.Entity.Rule;
import com.example.ruleEngine.ruleEngine.Entity.User;
import com.example.ruleEngine.ruleEngine.methods.RuleCombiner;
import com.example.ruleEngine.ruleEngine.services.RuleEvaluation;
import com.example.ruleEngine.ruleEngine.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@ComponentScan
@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;
    @Autowired
    private RuleRequest ruleRequest;
    @Autowired
    private EvaluationRequest evaluationRequest;
    @Autowired
    RuleEvaluation ruleEvaluation;
    private final RuleCombiner ruleCombiner;

    public RuleController(RuleCombiner ruleCombiner) {
        this.ruleCombiner = ruleCombiner;
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<Rule> createRule(@RequestBody RuleRequest ruleRequest){

        try{
            String ruleString = ruleRequest.getRuleString();
            List<String> rules = Arrays.asList(ruleString.split(","));
            if(rules.size()>1){
                RuleCombineRequest ruleCombineRequest=new RuleCombineRequest();
                ruleCombineRequest.setRules(rules);
                return combineRules(ruleCombineRequest);
            }
            ASTNode node=ruleService.parseRuleString(ruleString);
            Rule createdRule=ruleService.createRule(ruleString,node);

            return new ResponseEntity<>(createdRule, HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("/combine")
    public  ResponseEntity<Rule> combineRules(@RequestBody RuleCombineRequest ruleCombineRequest){

        try {
            List<String> rules = ruleCombineRequest.getRules();
            if (rules.size() == 1) {
                RuleRequest singleRuleRequest = new RuleRequest();
                singleRuleRequest.setRuleString(rules.get(0));
                return createRule(singleRuleRequest);
            }

            ASTNode combinedNode = ruleCombiner.rulescombine(rules,ruleCombineRequest.getCombineoperator());
            String combinedString = ruleService.parseASTToString(rules,ruleCombineRequest.getCombineoperator());
            Rule createdRule = ruleService.createRule(combinedString, combinedNode);
            return new ResponseEntity<>(createdRule, HttpStatus.CREATED);


        }
        catch(Exception e){

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }



    }
    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluateRule(@RequestBody EvaluationRequest evaluationRequest){

        try {
            ASTNode node = evaluationRequest.getNode();
            User user = evaluationRequest.getUserAttributes();
            boolean result = ruleEvaluation.evaluateRule(node, user);
            return new ResponseEntity<>(result, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }
}
