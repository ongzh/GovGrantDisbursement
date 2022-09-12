package com.meteortap.grantDisbursement.service;

import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.document.QualifiedHousehold;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GrantDisbursementServiceTest {

    @Mock
    private HouseholdRepository repository;

    @Mock
    private HouseholdService householdService;

    @InjectMocks
    private GrantDisbursementService grantService;


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
                LocalDate.of(1950,02,13), 0);

        member4 = new FamilyMember("m4", "member4", FamilyMember.Gender.FEMALE, FamilyMember.Occupation.STUDENT,
                FamilyMember.MaritalStatus.SINGLE, Optional.empty(),
                LocalDate.of(1980,02,13), 70000);

        //income>200000
        member5 = new FamilyMember("m5", "member5", FamilyMember.Gender.FEMALE, FamilyMember.Occupation.EMPLOYED,
                FamilyMember.MaritalStatus.SINGLE, Optional.empty(),
                LocalDate.of(1950,02,13), 300000);
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
    void getStudentEncouragementBonusEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = List.of(new QualifiedHousehold(List.of(member1),household1));
        given(householdService.getAllHousehold()).willReturn(householdTest);
        List<QualifiedHousehold> eligibiles = grantService.getStudentEncouragementBonusEligibility();

        assertEquals(qualifiedHouseholds, eligibiles);


    }

    @Test
    void getMultigenerationSchemeEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = List.of(new QualifiedHousehold(List.of(member1,member2), household1),
                new QualifiedHousehold(List.of(member3,member4),household2));
        given(householdService.getAllHousehold()).willReturn(householdTest);
        List<QualifiedHousehold> eligibiles = grantService.getMultigenerationSchemeEligibility();

        assertEquals(qualifiedHouseholds, eligibiles);
    }

    @Test
    void getElderBonusEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = List.of(new QualifiedHousehold(List.of(member3),household2));
        given(householdService.getAllHousehold()).willReturn(householdTest);
        List<QualifiedHousehold> eligibiles = grantService.getElderBonusEligibility();

        assertEquals(qualifiedHouseholds, eligibiles);

    }

    @Test
    void getBabySunshineGrantEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = List.of(new QualifiedHousehold(List.of(member6),household3));
        given(householdService.getAllHousehold()).willReturn(householdTest);
        List<QualifiedHousehold> eligibiles = grantService.getBabySunshineGrantEligibility();

        assertEquals(qualifiedHouseholds, eligibiles);

    }

    @Test
    void getYOLOGSTGrantEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = List.of(new QualifiedHousehold(List.of(member3, member4),household2));
        given(householdService.getAllHousehold()).willReturn(householdTest);
        List<QualifiedHousehold> eligibiles = grantService.getYOLOGSTGrantEligibility();

        assertEquals(qualifiedHouseholds, eligibiles);

    }
}