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



    @Id
    private String householdId;

    private List<FamilyMember> members;

    private HouseholdType householdType;

    private double householdIncome;

    public double calculateHouseholdIncome() {
        double calc = 0.0;
        for (FamilyMember member : members) {
            calc += member.getAnnualIncome();
        }
        return calc;
    }
}



