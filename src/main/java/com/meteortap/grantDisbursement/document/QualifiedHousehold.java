package com.meteortap.grantDisbursement.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualifiedHousehold{

    private List<FamilyMember> qualifiedMembers = new ArrayList<>();

    private Household household;
}
