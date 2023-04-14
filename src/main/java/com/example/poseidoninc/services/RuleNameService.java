package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.RuleName;
import com.example.poseidoninc.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List <RuleName> getAllRules() {
        return ruleNameRepository.findAll();
    }

    public void saveRule(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    public RuleName findRuleById(Integer id) {
        return ruleNameRepository.findById(id).orElse(null);
    }

    public void updateRuleName(Integer id, RuleName ruleName) {
        Optional <RuleName> optionalRuleName = ruleNameRepository.findById(id);
        if (optionalRuleName.isPresent()) {
            RuleName ruleToUpdate = optionalRuleName.get();
            ruleToUpdate.setName(ruleName.getName());
            ruleToUpdate.setDescription(ruleName.getDescription());
            ruleToUpdate.setJson(ruleName.getJson());
            ruleToUpdate.setTemplate(ruleName.getTemplate());
            ruleToUpdate.setSqlStr(ruleName.getSqlStr());
            ruleToUpdate.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(ruleToUpdate);
        }
    }

    public void deleteRuleNameById(Integer id) {
        ruleNameRepository.deleteById(id);
    }

}
