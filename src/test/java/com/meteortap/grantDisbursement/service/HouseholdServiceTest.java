package com.meteortap.grantDisbursement.service;

import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.repository.HouseholdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HouseholdServiceTest {

    @Mock
    private HouseholdRepository repository;

    @InjectMocks
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
    void getAllHousehold() {
        given(repository.findAll()).willReturn(householdTest);
        List<Household> households = service.getAllHousehold();
        assertThat(households).isNotNull();
        assertEquals(households, householdTest);
        assertEquals(households.size(), householdTest.size());

    }

    @Test
    void getHouseholdById() {
        given(repository.findById("h1")).willReturn(Optional.of(household1));
        Household household = service.getHouseholdById("h1");
        assertThat(household).isNotNull();
        assertEquals(household, household1);
    }

    @Test
    void getHouseholdByMember() {
        given(repository.findAll()).willReturn(householdTest);
        Household household = service.getHouseholdByMember("m5");
        assertThat(household).isNotNull();
        assertEquals(household.getMembers().size(), household3.getMembers().size());
        assertEquals(household.getHouseholdType(), household3.getHouseholdType());
    }

    @Test
    void createHousehold() {
        given(repository.save(household1)).willReturn(household1);
        Household savedHousehold = service.createHousehold(household1);
        assertThat(household1).isNotNull();
        assertEquals(savedHousehold, household1);
    }

    @Test
    void deleteHouseholdById() {
        service.deleteHouseholdById("h1");
        verify(repository, times(1)).deleteById("h1");
    }

    @Test
    void updateHouseholdType() {
        given(repository.findById("h1")).willReturn(Optional.of(household1));
        Household toUpdate = repository.findById("h1").get();
        given(repository.save(toUpdate)).willReturn(toUpdate);
        toUpdate.setHouseholdType(Household.HouseholdType.LANDED);
        Household updated = service.updateHousehold("h1", toUpdate);
        assertThat(updated).isNotNull();
        assertEquals(updated.getHouseholdType(), Household.HouseholdType.LANDED);
    }
}