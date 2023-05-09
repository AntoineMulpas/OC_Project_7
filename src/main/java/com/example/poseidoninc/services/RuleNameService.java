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

    public RuleName saveRule(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public RuleName findRuleById(Integer id) {
        return ruleNameRepository.findById(id).orElse(null);
    }

    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        Optional <RuleName> optionalRuleName = ruleNameRepository.findById(id);
        if (optionalRuleName.isPresent()) {
            optionalRuleName.get().setName(ruleName.getName());
            optionalRuleName.get().setDescription(ruleName.getDescription());
            optionalRuleName.get().setJson(ruleName.getJson());
            optionalRuleName.get().setTemplate(ruleName.getTemplate());
            optionalRuleName.get().setSqlStr(ruleName.getSqlStr());
            optionalRuleName.get().setSqlPart(ruleName.getSqlPart());
            return ruleNameRepository.save(optionalRuleName.get());
        }
        return null;
    }

    public boolean deleteRuleNameById(Integer id) {
        if (ruleNameRepository.findById(id).isPresent()) {
            ruleNameRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
