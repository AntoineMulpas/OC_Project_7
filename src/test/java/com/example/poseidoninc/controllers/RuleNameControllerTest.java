package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.domain.RuleName;
import com.example.poseidoninc.services.BidListService;
import com.example.poseidoninc.services.RuleNameService;
import com.example.poseidoninc.services.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = RuleNameController.class)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    UserAuthenticationService userAuthenticationService;

    @Test
    @WithMockUser
    void home() throws Exception {
        when(ruleNameService.getAllRules()).thenReturn(List.of(new RuleName(), new RuleName()));
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> bidList = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("rules");
                    assertEquals(2, bidList.size());
                });
    }

    @Test
    @WithMockUser
    void addRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate() {
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(ruleNameService.findRuleById(1)).thenReturn(new RuleName());
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRuleName() {
    }

    @Test
    @WithMockUser
    void deleteRuleName() throws Exception {
        when(ruleNameService.deleteRuleNameById(1)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}