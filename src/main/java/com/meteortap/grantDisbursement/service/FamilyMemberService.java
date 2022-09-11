package com.meteortap.grantDisbursement.service;



import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.exception.ResourceNotFoundException;
import com.meteortap.grantDisbursement.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public List<FamilyMember> getAllFamilyMembers() {
        return familyMemberRepository.findAll();
    }

    public FamilyMember getFamilyMemberById(String familyMemberId) {
        return familyMemberRepository.findById(familyMemberId)
                .orElseThrow(()-> new ResourceNotFoundException("FamilyMember with id:"+familyMemberId+" does not exist"));
    }

    public void deleteFamilyMemberById(String familyMemberId) {
        familyMemberRepository.deleteById(familyMemberId);
    }

    public FamilyMember createFamilyMember(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    public FamilyMember updateFamilyMember(String familyMemberId, FamilyMember updatedDetails) {
        FamilyMember updateFamilyMember = familyMemberRepository.findById(familyMemberId)
                .orElseThrow(()-> new ResourceNotFoundException("FamilyMember with id:"+familyMemberId+" does not exist"));

        updateFamilyMember.setAnnualIncome(updatedDetails.getAnnualIncome());
        updateFamilyMember.setDob(updatedDetails.getDob());
        updateFamilyMember.setGender(updatedDetails.getGender());
        updateFamilyMember.setName(updatedDetails.getName());
        updateFamilyMember.setMaritalStatus(updatedDetails.getMaritalStatus());
        updateFamilyMember.setSpouseName(updatedDetails.getSpouseName());
        updateFamilyMember.setOccupation(updatedDetails.getOccupation());

        return familyMemberRepository.save(updateFamilyMember);

    }

}
