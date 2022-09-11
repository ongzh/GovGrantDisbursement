package com.meteortap.grantDisbursement.repository;

import com.meteortap.grantDisbursement.document.FamilyMember;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FamilyMemberRepository extends MongoRepository<FamilyMember, String> {


}
