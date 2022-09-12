package com.meteortap.grantDisbursement.controller;

import com.meteortap.grantDisbursement.document.QualifiedHousehold;
import com.meteortap.grantDisbursement.service.GrantDisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/grantDisbursement")
public class GrantDisbursementController {


    @Autowired
    private GrantDisbursementService grantDisbursementService;

    @GetMapping("/getStudentEncouragementBonusEligibility")
    public List<QualifiedHousehold> getStudentEncouragementBonusEligibility(){
        return grantDisbursementService.getStudentEncouragementBonusEligibility();
    }

    @GetMapping("/getMultigenerationSchemeEligibility")
    public List<QualifiedHousehold> getMultigenerationSchemeEligibility(){
        return grantDisbursementService.getMultigenerationSchemeEligibility();
    }

    @GetMapping("/getElderBonusEligibility")
    public List<QualifiedHousehold> getElderBonusEligibility(){
        return grantDisbursementService.getElderBonusEligibility();
    }

    @GetMapping("/getBabySunshineGrantEligibility")
    public List<QualifiedHousehold> getBabySunshineGrantEligibility(){
        return grantDisbursementService.getBabySunshineGrantEligibility();
    }

    @GetMapping("/getYOLOGSTGrantEligibility")
    public List<QualifiedHousehold> getYOLOGSTGrantEligibility(){
        return grantDisbursementService.getYOLOGSTGrantEligibility();
    }

}
