package com.meteortap.grantDisbursement.repository;

import com.meteortap.grantDisbursement.document.Household;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseholdRepository extends MongoRepository<Household, String> {


    Household findHouseholdByFamilyMember(String familyMemberId);

}
