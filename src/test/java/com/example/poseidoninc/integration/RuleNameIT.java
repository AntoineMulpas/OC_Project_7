package com.example.poseidoninc.integration;

import com.example.poseidoninc.domain.RuleName;
import com.example.poseidoninc.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Test
    @WithMockUser
    void home() throws Exception {
        when(ruleNameRepository.findAll()).thenReturn(List.of(new RuleName(), new RuleName()));
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> bidList = (List<?>) Objects.requireNonNull(result.getModelAndView()).getModel().get("rules");
                    assertEquals(2, bidList.size());
                });
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(new RuleName()));
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteRuleName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection());
    }

}
