package com.meteortap.grantDisbursement.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Households")
public class Household {

    public enum HouseholdType {
        HDB,
        CONDOMINIUM,
        LANDED
    }

    public Household(String householdId, List<FamilyMember> members, HouseholdType householdType) {
        this.householdId = householdId;
        this.members = members;
        this.householdType = householdType;
        this.householdIncome = 0;
        for (FamilyMember member: members){
            this.householdIncome += member.getAnnualIncome();
        }
    }


    @Id
    private String householdId;

    private List<FamilyMember> members;

    private HouseholdType householdType;

    private double householdIncome;



}
