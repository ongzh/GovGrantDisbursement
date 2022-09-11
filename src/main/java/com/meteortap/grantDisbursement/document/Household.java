package com.meteortap.grantDisbursement.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document("Households")
public class Household {

    @Id
    private final String householdId;

    private List<FamilyMember> members;

    private HouseholdType householdType;

    private double householdIncome;

}
