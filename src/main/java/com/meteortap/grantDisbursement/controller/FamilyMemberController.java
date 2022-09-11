package com.meteortap.grantDisbursement.controller;


import com.meteortap.grantDisbursement.document.FamilyMember;
import com.meteortap.grantDisbursement.service.FamilyMemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/FamilyMember")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @GetMapping("/getAll")
    public List<FamilyMember> getAllFamilyMembers(){
        return familyMemberService.getAllFamilyMembers();
    }

    @GetMapping("/getById/{familyMemberId}")
    public FamilyMember getFamilyMemberById(@PathVariable String familyMemberId){
        return familyMemberService.getFamilyMemberById(familyMemberId);
    }

    @PostMapping("/create")
    public FamilyMember createFamilyMember(@RequestBody FamilyMember familyMember){
        return familyMemberService.createFamilyMember(familyMember);
    }

    @DeleteMapping("/delete/{familyMemberId}")
    public void deleteFamilyMemberById(@PathVariable String familyMemberId){
        familyMemberService.deleteFamilyMemberById(familyMemberId);
    }

    @PutMapping("update/{familyMemberId}")
    public FamilyMember updateFamilyMember(@PathVariable String familyMemberId, @RequestBody FamilyMember updatedDetails){
        return familyMemberService.updateFamilyMember(familyMemberId, updatedDetails);

    }
}
