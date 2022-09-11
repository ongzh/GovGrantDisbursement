package com.meteortap.grantDisbursement.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Document("FamilyMembers")
public class FamilyMember {

    public enum Occupation {
        UNEMPLOYED,
        STUDENT,
        EMPLOYED
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum MaritalStatus {
        SINGLE,
        MARRIED
    }


    @Id
    private final String familyMemberId;

    private String name;

    private Gender gender;

    private Occupation occupation;

    private MaritalStatus maritalStatus;

    private String spouseName;

    private LocalDate dob;

    private double annualIncome;

}
