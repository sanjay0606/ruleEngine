package com.example.ruleEngine.ruleEngine.Repositry;

import com.example.ruleEngine.ruleEngine.Entity.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


@Component
public interface RuleRepositry extends MongoRepository <Rule,String>{
}
