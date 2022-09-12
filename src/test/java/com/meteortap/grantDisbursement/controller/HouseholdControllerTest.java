package com.meteortap.grantDisbursement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.service.FamilyMemberService;
import com.meteortap.grantDisbursement.service.HouseholdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HouseholdController.class)
class HouseholdControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private HouseholdService service;

    private List<Household> householdTest;
    private Household household1;
    private Household household2;
    private Household household3;

    private FamilyMember member1;
    private FamilyMember member2;
    private FamilyMember member3;
    private FamilyMember member4;
    private FamilyMember member5;
    private FamilyMember member6;

    @BeforeEach
    void setUp() {
        //<16, Student
        member1 = new FamilyMember("m1", "member1", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, Optional.of("m2"),
                LocalDate.of(2010,02,13), 0);

        member2 = new FamilyMember("m2", "member2", FamilyMember.Gender.FEMALE, FamilyMember.Occupation.EMPLOYED,
                FamilyMember.MaritalStatus.SINGLE, Optional.of("m1"),
                LocalDate.of(1980,02,13), 70000);
        //>55, unemployed
        member3 = new FamilyMember("m3", "member3", FamilyMember.Gender.MALE, FamilyMember.Occupation.UNEMPLOYED,
                FamilyMember.MaritalStatus.SINGLE, Optional.empty(),
                LocalDate.of(1950,02,13), 70000);

        member4 = new FamilyMember("m4", "member4", FamilyMember.Gender.FEMALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, Optional.empty(),
                LocalDate.of(1980,02,13), 70000);

        //>55, unemployed
        member5 = new FamilyMember("m5", "member5", FamilyMember.Gender.FEMALE, FamilyMember.Occupation.UNEMPLOYED,
                FamilyMember.MaritalStatus.SINGLE, Optional.empty(),
                LocalDate.of(1950,02,13), 70000);
        //Baby
        member6 = new FamilyMember("m6", "member6", FamilyMember.Gender.FEMALE, FamilyMember.Occupation.UNEMPLOYED,
                FamilyMember.MaritalStatus.SINGLE, Optional.empty(),
                LocalDate.of(2022,05,13), 0);

        household1 = new Household("h1", List.of(member1,member2), Household.HouseholdType.LANDED);

        household2 = new Household("h2", List.of(member3,member4), Household.HouseholdType.HDB);

        household3 = new Household("h3", List.of(member5,member6), Household.HouseholdType.CONDOMINIUM);

        householdTest = List.of(household1,household2,household3);
    }

    @Test
    void getAllHousehold() throws Exception {
        given(service.getAllHousehold()).willReturn(householdTest);
        mvc.perform(get("/api/household/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].householdId", is("h2")));
    }

    @Test
    void getHouseholdById() throws Exception{
        given(service.getHouseholdById("h1")).willReturn((household1));
        mvc.perform(get("/api/household/getById/h1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.householdId", is("h1")));
    }

    @Test
    void getHouseholdByMember() throws Exception {
        given(service.getHouseholdByMember("m1")).willReturn((household1));
        mvc.perform(get("/api/household/getByMember/m1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.householdId", is("h1")));
    }

    @Test
    void createHousehold() throws Exception {
        Household toPost = new Household(null, List.of(member1,member2), Household.HouseholdType.LANDED);

        Household toReturn = new Household("toPost", List.of(member1,member2), Household.HouseholdType.LANDED, 70000);

        given(service.createHousehold(toPost)).willReturn(toReturn);
        mvc.perform(post("/api/household/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(toPost)))

                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.householdId", is("toPost")))
                .andExpect(jsonPath("$.householdType", is("LANDED")))
                .andExpect(jsonPath("$.householdIncome", is(70000.0)))
                .andExpect(jsonPath("$.members", hasSize(2)));;
    }

    @Test
    void deleteHouseholdById() throws Exception {

        when(service.getHouseholdById("h1")).thenReturn(household1);

        mvc.perform(delete("/api/household/delete/h1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}