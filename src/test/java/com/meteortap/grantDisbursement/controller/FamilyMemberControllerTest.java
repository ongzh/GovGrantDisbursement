package com.meteortap.grantDisbursement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.service.FamilyMemberService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = FamilyMemberController.class)
class FamilyMemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FamilyMemberService service;

    private List<FamilyMember> memberTest;
    private FamilyMember member1;
    private FamilyMember member2;
    private FamilyMember member3;

    @BeforeEach
    void setUp() {

         member1 = new FamilyMember("m1", "member1", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, "m2", LocalDate.of(1980,02,13), 70000);

         member2 = new FamilyMember("m2", "member2", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, "m1", LocalDate.of(1980,02,13), 70000);

         member3 = new FamilyMember("m3", "member3", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, "", LocalDate.of(1980,02,13), 70000);

        memberTest = List.of(member1,member2,member3);
    }


    @Test
    void getAllFamilyMembers() throws Exception{

        given(service.getAllFamilyMembers()).willReturn(memberTest);
        mvc.perform(get("/api/familyMember/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("member2")));

    }

    @Test
    void getFamilyMemberById() throws Exception{
        given(service.getFamilyMemberById("m1")).willReturn((member1));
        mvc.perform(get("/api/familyMember/getById/m1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("member1")));

    }

    @Test
    void createFamilyMember() throws Exception {
        FamilyMember toPost = new FamilyMember(null, "memberToPost", FamilyMember.Gender.FEMALE,
                FamilyMember.Occupation.STUDENT, FamilyMember.MaritalStatus.SINGLE, "",
                LocalDate.of(2022,01,23),0);

        FamilyMember toReturn = new FamilyMember("postID", "memberToPost", FamilyMember.Gender.FEMALE,
                FamilyMember.Occupation.STUDENT, FamilyMember.MaritalStatus.SINGLE, "",
                LocalDate.of(2022,01,23),0);

        given(service.createFamilyMember(toPost)).willReturn(toReturn);
        mvc.perform(post("/api/familyMember/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(toPost)))

                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.familyMemberId", is("postID")))
                .andExpect(jsonPath("$.name", is("memberToPost")))
                .andExpect(jsonPath("$.gender", is("FEMALE")))
                .andExpect(jsonPath("$.occupation", is("STUDENT")))
                .andExpect(jsonPath("$.maritalStatus", is("SINGLE")));

    }

    @Test
    void deleteFamilyMemberById() throws Exception{
        when(service.getFamilyMemberById("m1")).thenReturn(member1);

        mvc.perform(delete("/api/familyMember/delete/m1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}