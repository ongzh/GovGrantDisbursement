package com.meteortap.grantDisbursement.controller;


import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.document.Household;
import com.meteortap.grantDisbursement.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Household")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @GetMapping("/getAll")
    public List<Household> getAllHousehold() {
        return householdService.getAllHousehold();
    }

    @GetMapping("/getById/{householdId}")
    public Household getHouseholdById(@PathVariable String householdId) {
        return householdService.getHouseholdById(householdId);
    }

    @GetMapping("/getByMember/{familyMemberId}")
    public Household getHouseholdByMember(@PathVariable String familyMemberId){
        return householdService.getHouseholdByMember(familyMemberId);
    }
    /**
     * get all households with  householdIncome < maxIncome
     */
    @GetMapping("/getHouseholdsByIncome/{income}")
    public List<Household> getHouseholdsByIncome(@PathVariable String maxIncome){
        return householdService.getHouseholdsByIncome(maxIncome);
    }

    @PostMapping("/create")
    public Household createHousehold(@RequestBody Household household){
        return householdService.createHousehold(household);
    }

    @DeleteMapping("/delete/{householdId}")
    public void deleteHouseholdById(@PathVariable String familyMemberId){
        householdService.deleteHouseholdById(familyMemberId);
    }

    @PutMapping("update/{householdId}")
    public Household updateHousehold(@PathVariable String householdId, @RequestBody Household updatedDetails){
        return householdService.updateHousehold(householdId, updatedDetails);

    }



}
