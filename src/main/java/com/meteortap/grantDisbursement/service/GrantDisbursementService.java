package com.meteortap.grantDisbursement.service;


import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.document.QualifiedHousehold;
import com.meteortap.grantDisbursement.util.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrantDisbursementService {

    @Autowired
    private HouseholdService householdService;

    public List<QualifiedHousehold> getStudentEncouragementBonusEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = new ArrayList<>();

        for (Household household: householdService.getAllHousehold()){
            if (household.getHouseholdIncome()>=200000){
                continue;
            }
            QualifiedHousehold qualifiedHousehold = new QualifiedHousehold();
            List<FamilyMember> qualifiedMembers = new ArrayList<>();
            for (FamilyMember member: household.getMembers()){
                if (member.getOccupation().equals(FamilyMember.Occupation.STUDENT) && AgeCalculator.getAgeInYears(member.getDob())<16){
                    qualifiedMembers.add(member);
                }
            }
            if (!qualifiedMembers.isEmpty()){
                qualifiedHousehold.setHousehold(household);
                qualifiedHousehold.setQualifiedMembers(qualifiedMembers);
                qualifiedHouseholds.add(qualifiedHousehold);
            }
        }
        return qualifiedHouseholds;
    }

    public List<QualifiedHousehold> getMultigenerationSchemeEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = new ArrayList<>();

        for (Household household: householdService.getAllHousehold()){

            QualifiedHousehold qualifiedHousehold = new QualifiedHousehold();
            for (FamilyMember member: household.getMembers()){
                if ((AgeCalculator.getAgeInYears(member.getDob())>55) || AgeCalculator.getAgeInYears(member.getDob())<18){
                    qualifiedHousehold.setHousehold(household);
                    qualifiedHousehold.setQualifiedMembers(household.getMembers());
                    qualifiedHouseholds.add(qualifiedHousehold);
                }
            }

        }
        return qualifiedHouseholds;
    }

    public List<QualifiedHousehold> getElderBonusEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = new ArrayList<>();

        for (Household household: householdService.getAllHousehold()){
            if (!household.getHouseholdType().equals(Household.HouseholdType.HDB)){
                continue;
            }
            QualifiedHousehold qualifiedHousehold = new QualifiedHousehold();
            List<FamilyMember> qualifiedMembers = new ArrayList<>();
            for (FamilyMember member: household.getMembers()){
                if (AgeCalculator.getAgeInYears(member.getDob())>=55){
                    qualifiedMembers.add(member);
                }
            }
            if (!qualifiedMembers.isEmpty()){
                qualifiedHousehold.setHousehold(household);
                qualifiedHousehold.setQualifiedMembers(qualifiedMembers);
                qualifiedHouseholds.add(qualifiedHousehold);
            }
        }
        return qualifiedHouseholds;
    }

    public List<QualifiedHousehold> getBabySunshineGrantEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = new ArrayList<>();

        for (Household household: householdService.getAllHousehold()){

            QualifiedHousehold qualifiedHousehold = new QualifiedHousehold();
            List<FamilyMember> qualifiedMembers = new ArrayList<>();
            for (FamilyMember member: household.getMembers()){
                if (AgeCalculator.getAgeInMonths(member.getDob())<8){
                    qualifiedMembers.add(member);
                }
            }
            if (!qualifiedMembers.isEmpty()){
                qualifiedHousehold.setHousehold(household);
                qualifiedHousehold.setQualifiedMembers(qualifiedMembers);
                qualifiedHouseholds.add(qualifiedHousehold);
            }
        }
        return qualifiedHouseholds;
    }

    public List<QualifiedHousehold> getYOLOGSTGrantEligibility() {
        List<QualifiedHousehold> qualifiedHouseholds = new ArrayList<>();

        for (Household household: householdService.getAllHousehold()){
            if (!household.getHouseholdType().equals(Household.HouseholdType.HDB) || household.getHouseholdIncome()>100000){
                continue;
            }
            QualifiedHousehold qualifiedHousehold = new QualifiedHousehold();
            qualifiedHousehold.setHousehold(household);
            qualifiedHousehold.setQualifiedMembers(household.getMembers());
            qualifiedHouseholds.add(qualifiedHousehold);
            }

        return qualifiedHouseholds;
    }
}
