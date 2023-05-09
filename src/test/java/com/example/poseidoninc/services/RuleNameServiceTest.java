package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.RuleName;
import com.example.poseidoninc.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    private RuleNameService underTest;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    void setUp() {
        underTest = new RuleNameService(ruleNameRepository);
    }

    @Test
    void getAllRules() {
        when(ruleNameRepository.findAll()).thenReturn(List.of(new RuleName(), new RuleName()));
        assertEquals(2, underTest.getAllRules().size());
    }

    @Test
    void saveRule() {
        RuleName ruleName = new RuleName();
        when(ruleNameRepository.save(any())).thenReturn(ruleName);
        assertEquals(ruleName, underTest.saveRule(ruleName));
    }

    @Test
    void findRuleById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(new RuleName()));
        assertNotNull(underTest.findRuleById(1));
    }

    @Test
    void findRuleByIdShouldReturnNull() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.findRuleById(1));
    }

    @Test
    void updateRuleName() {
        RuleName ruleName = new RuleName();
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any())).thenReturn(ruleName);
        assertNotNull(underTest.updateRuleName(1, ruleName));
    }

    @Test
    void updateRuleNameShouldReturnNull() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.updateRuleName(1, new RuleName()));
    }

    @Test
    void deleteRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(new RuleName()));
        assertTrue(underTest.deleteRuleNameById(1));
    }

    @Test
    void deleteRuleNameByIdShouldReturnFalse() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(underTest.deleteRuleNameById(1));
    }
}