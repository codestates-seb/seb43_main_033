package main.main.salarystatement.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorTest {

    @DisplayName("기본 공제액 계산 테스트")
    @Test
    public void getBasicDeduction() {
        BigDecimal toCalculate = BigDecimal.valueOf(5000000);

        BigDecimal expected = Calculator.calculateHealthInsurance(toCalculate)
                .add(Calculator.calculateNationalCoalition(toCalculate))
                .add(Calculator.calculateEmploymentInsurance(toCalculate));
        BigDecimal actual = Calculator.getBasicDeduction(toCalculate);

        assertEquals(expected, actual);
    }

    @DisplayName("소득 구간별 세액 계산 테스트")
    @Test
    public void calculateTax() {
        BigDecimal taxBase1 = BigDecimal.valueOf(14000000);
        BigDecimal taxBase2 = BigDecimal.valueOf(50000000);
        BigDecimal taxBase3 = BigDecimal.valueOf(88000000);
        BigDecimal taxBase4 = BigDecimal.valueOf(150000000);
        BigDecimal taxBase5 = BigDecimal.valueOf(300000000);
        BigDecimal taxBase6 = BigDecimal.valueOf(500000000);
        BigDecimal taxBase7 = BigDecimal.valueOf(1000000000);

        assertTrue(BigDecimal.valueOf(70000.00).compareTo(Calculator.getIncomeTax(taxBase1)) == 0);
        assertTrue(BigDecimal.valueOf(520000.00).compareTo(Calculator.getIncomeTax(taxBase2)) == 0);
        assertTrue(BigDecimal.valueOf(1280000.00).compareTo(Calculator.getIncomeTax(taxBase3)) == 0);
        assertTrue(BigDecimal.valueOf(3088333.33).compareTo(Calculator.getIncomeTax(taxBase4)) == 0);
        assertTrue(BigDecimal.valueOf(7838333.33).compareTo(Calculator.getIncomeTax(taxBase5)) == 0);
        assertTrue(BigDecimal.valueOf(14505000.0).compareTo(Calculator.getIncomeTax(taxBase6)) == 0);
        assertTrue(BigDecimal.valueOf(47755000.00).compareTo(Calculator.getIncomeTax(taxBase7)) == 0);
    }
}
