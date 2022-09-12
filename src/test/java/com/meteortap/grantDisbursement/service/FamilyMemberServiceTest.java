package com.meteortap.grantDisbursement.service;

import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.repository.FamilyMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FamilyMemberServiceTest {

    @Mock
    private FamilyMemberRepository repository;

    @InjectMocks
    private HouseholdService householdService;
    @InjectMocks
    private FamilyMemberService familyMemberService;

    private List<FamilyMember> memberTest;
    private FamilyMember member1;
    private FamilyMember member2;
    private FamilyMember member3;

    @BeforeEach
    void setUp() {
        member1 = new FamilyMember("m1", "member1", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, "m2",
                LocalDate.of(1980,02,13), 70000);

        member2 = new FamilyMember("m2", "member2", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, "m1",
                LocalDate.of(1980,02,13), 70000);

        member3 = new FamilyMember("m3", "member3", FamilyMember.Gender.MALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, "",
                LocalDate.of(1980,02,13), 70000);

        memberTest = List.of(member1,member2,member3);
    }

    @Test
    void getAllFamilyMembers() {
        given(repository.findAll()).willReturn(memberTest);
        List<FamilyMember> familyMembers = familyMemberService.getAllFamilyMembers();
        assertThat(familyMembers).isNotNull();
        assertEquals(familyMembers, memberTest);
        assertEquals(familyMembers.size(), memberTest.size());
    }

    @Test
    void getFamilyMemberById() {
        given(repository.findById("m1")).willReturn(Optional.of(member1));
        FamilyMember member = familyMemberService.getFamilyMemberById("m1");
        assertThat(member).isNotNull();
        assertEquals(member, member1);
    }

    @Test
    void deleteFamilyMemberById() {
        familyMemberService.deleteFamilyMemberById("m1");
        verify(repository, times(1)).deleteById("m1");
    }

    @Test
    void createFamilyMember() {
        given(repository.save(member1)).willReturn(member1);
        FamilyMember savedMember = familyMemberService.createFamilyMember(member1);
        assertThat(savedMember).isNotNull();
        assertEquals(savedMember, member1);
    }

    @Test
    void updateFamilyMemberName() {
        given(repository.findById("m1")).willReturn(Optional.of(member1));
        FamilyMember toUpdate = repository.findById("m1").get();
        given(repository.save(toUpdate)).willReturn(toUpdate);
        toUpdate.setName("updatedMember");
        FamilyMember updated = familyMemberService.updateFamilyMember("m1", toUpdate);
        assertThat(updated).isNotNull();
        assertEquals(updated.getName(), "updatedMember");
    }
}