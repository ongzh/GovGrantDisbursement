package com.meteortap.grantDisbursement.util;


import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    public static int getAgeInYears(LocalDate dob) {
        if (dob != null) {
            return Period.between(dob, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public static int getAgeInMonths(LocalDate dob) {
        if (dob != null) {
            return Period.between(dob, LocalDate.now()).getMonths();
        } else {
            return 0;
        }
    }
}
