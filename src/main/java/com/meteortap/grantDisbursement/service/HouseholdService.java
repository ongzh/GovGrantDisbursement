package com.meteortap.grantDisbursement.service;

import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.exception.ResourceNotFoundException;
import com.meteortap.grantDisbursement.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    public List<Household> getAllHousehold() {
        return householdRepository.findAll();
    }

    public Household getHouseholdById(String householdId) {
        return householdRepository.findById(householdId)
                .orElseThrow(()-> new ResourceNotFoundException("Household with id:"+householdId+" does not exist"));
    }

    //To improve time complexity/performance, an alternative approach can be to store houseHoldId in each familyMember
    public Household getHouseholdByMember(String familyMemberId) throws ResourceNotFoundException{

        //To improve time complexity/performance, an alternative approach can be to store houseHoldId in each familyMember

        List<Household> allHouseholds = householdRepository.findAll();

        for (Household h: allHouseholds){
            for (FamilyMember m: h.getMembers()){
                if (m.getFamilyMemberId().equals(familyMemberId)){
                    return h;
                }
            }
        }
        throw new ResourceNotFoundException("familyMemberId does not belong to any household");


    }

    public List<Household> getHouseholdsByIncome(String maxIncome) {
        List<Household> households = new ArrayList<>();
        double income = Double.parseDouble(maxIncome);
        List<Household> allHouseholds = householdRepository.findAll();
        for (Household household: allHouseholds){
            if (household.getHouseholdIncome()<income){
                households.add(household);
            }
        }
        return households;
    }

    public Household createHousehold(Household household) {

        if (household.getHouseholdIncome()==0.0){
            household.setHouseholdIncome(household.calculateHouseholdIncome());
        }
        return householdRepository.save(household);
    }

    public void deleteHouseholdById(String householdId) {
        householdRepository.deleteById(householdId);
    }

    public Household updateHousehold(String householdId, Household updatedDetails) {
        Household household = householdRepository.findById(householdId)
                .orElseThrow(()-> new ResourceNotFoundException("Household with id:"+householdId+" does not exist"));

        household.setHouseholdType(updatedDetails.getHouseholdType());
        household.setHouseholdIncome(updatedDetails.getHouseholdIncome());
        household.setMembers(updatedDetails.getMembers());

        householdRepository.save(household);

        return household;

    }

    public Household addMemberToHousehold(String householdId, FamilyMember member) {
        Household household = householdRepository.findById(householdId)
                .orElseThrow(()-> new ResourceNotFoundException("Household with id:"+householdId+" does not exist"));

        household.getMembers().add(member);

        householdRepository.save(household);
        
        return household;
    }
}
