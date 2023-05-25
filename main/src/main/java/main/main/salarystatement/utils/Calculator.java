package main.main.salarystatement.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    // 고용 보험
    public static BigDecimal calculateEmploymentInsurance(BigDecimal basicSalary) {
        return basicSalary.multiply(BigDecimal.valueOf(0.008));
    }


    // 건강 보험
    public static BigDecimal calculateHealthInsurance(BigDecimal basicSalary) {
        return basicSalary.multiply(BigDecimal.valueOf(0.0343));
    }


    // 국민 연금
    public static BigDecimal calculateNationalCoalition(BigDecimal basicSalary) {
        return basicSalary.multiply(BigDecimal.valueOf(0.045));
    }

    public static BigDecimal calculateTax(BigDecimal basicSalary) {
        BigDecimal taxBase = getTaxBase(basicSalary);
        return getIncomeTax(taxBase);
    }

    // 소득세 계산
    public static BigDecimal getIncomeTax(BigDecimal taxBase) {
        BigDecimal incomeTex;
        if (taxBase.compareTo(BigDecimal.valueOf(14000000)) < 1) {
            incomeTex = taxBase.multiply(BigDecimal.valueOf(0.06)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(50000000)) < 1) {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(14000000)).multiply(BigDecimal.valueOf(0.15)).add(BigDecimal.valueOf(840000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(88000000)) < 1) {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(50000000)).multiply(BigDecimal.valueOf(0.24)).add(BigDecimal.valueOf(6240000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(150000000)) < 1) {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(88000000)).multiply(BigDecimal.valueOf(0.35)).add(BigDecimal.valueOf(15360000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(300000000)) < 1) {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(150000000)).multiply(BigDecimal.valueOf(0.38)).add(BigDecimal.valueOf(37060000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(500000000)) < 1) {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(300000000)).multiply(BigDecimal.valueOf(0.4)).add(BigDecimal.valueOf(94060000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(1000000000)) < 1) {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(50000000)).multiply(BigDecimal.valueOf(0.42)).add(BigDecimal.valueOf(174060000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        } else {
            incomeTex = taxBase.subtract(BigDecimal.valueOf(1000000000)).multiply(BigDecimal.valueOf(0.45)).add(BigDecimal.valueOf(384060000)).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        }
        return incomeTex;
    }

    // 기본공제
    public static BigDecimal getTaxBase(BigDecimal basicSalary) {
        BigDecimal taxBase = basicSalary.subtract(getBasicDeduction(basicSalary)).multiply(BigDecimal.valueOf(12));
        return taxBase;
    }

    public static BigDecimal getBasicDeduction(BigDecimal basicSalary) {
        return basicSalary.multiply(BigDecimal.valueOf(0.0873));
    }
}
