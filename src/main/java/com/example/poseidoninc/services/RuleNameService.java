package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.RuleName;
import com.example.poseidoninc.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This Class is the Service layer for the RuleName object.
 * It is annotated with the @Transaction to manage transactions of
 * all the methods contained in this class.
 */

@Service
@Transactional
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * This method is used to retrieve all RuleNames
     * @return List of all RuleNames
     */

    public List <RuleName> getAllRules() {
        return ruleNameRepository.findAll();
    }

    /**
     * This method is used to save a new RuleName
     * @param ruleName
     * @return It returns the saved RuleName.
     */

    public RuleName saveRule(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * This method is used to find a specific RuleName by id.
     * @param id
     * @return RuleName found by id or null.
     */

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

    /**
     * This method is used to delete a specific RuleName found by id.
     * @param id
     * @return a boolean depending on the outcome of the operation.
     */

    public boolean deleteRuleNameById(Integer id) {
        if (ruleNameRepository.findById(id).isPresent()) {
            ruleNameRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
