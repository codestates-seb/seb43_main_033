package main.main.salarystatement.utils;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.companymember.entity.CompanyMember;
import main.main.companymember.service.CompanyMemberService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.service.LaborContractService;
import main.main.member.entity.Member;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.service.StatusOfWorkService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static main.main.salarystatement.utils.Calculator.*;
import static main.main.salarystatement.utils.Calculator.calculateEmploymentInsurance;
@RequiredArgsConstructor
@Component
public class SalaryStatementMaker {
    private final CompanyService companyService;
    private final CompanyMemberService companyMemberService;
    private final StatusOfWorkService statusOfWorkService;
    private final LaborContractService laborContractService;

    public SalaryStatement makeContent(long companyId, long companyMemberId, int year, int month) {
        SalaryStatement salaryStatement = new SalaryStatement();
        CompanyMember companyMember = companyMemberService.findCompanyMember(companyMemberId);
        Member member = companyMember.getMember();
        Company company = companyService.findCompany(companyId);

        if (!company.getCompanyId().equals(companyId)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_STATUS);
        }

        List<StatusOfWork> statusOfWorks = statusOfWorkService.findStatusOfWorks(year, month, member.getMemberId());
        LaborContract laborContract = laborContractService.findLaborContractForSalaryStatement(member, company, year, month);
        BigDecimal basicSalary = laborContract.getBasicSalary();
        BigDecimal hourlyWage = basicSalary.divide(BigDecimal.valueOf(209), RoundingMode.HALF_UP);
        int time;
        BigDecimal overtimePay = BigDecimal.ZERO;
        int overtimePayBasis = 0;
        BigDecimal nightWorkAllowance = BigDecimal.ZERO;
        int nightWorkAllowanceBasis = 0;
        BigDecimal holidayWorkAllowance = BigDecimal.ZERO;
        int holidayWorkAllowanceBasis = 0;
        BigDecimal unpaidLeave = BigDecimal.ZERO;

        for (StatusOfWork statusOfWork : statusOfWorks) {
            switch (statusOfWork.getNote()) {
                case 연장근로:
                    time = (int) ChronoUnit.HOURS.between(statusOfWork.getStartTime(), statusOfWork.getFinishTime());
                    overtimePayBasis += time;
                    overtimePay = overtimePay.add(hourlyWage.multiply(BigDecimal.valueOf(time)).multiply(BigDecimal.valueOf(statusOfWork.getNote().getRate())));
                    break;
                case 야간근로:
                    time = (int) ChronoUnit.HOURS.between(statusOfWork.getStartTime(), statusOfWork.getFinishTime());
                    nightWorkAllowanceBasis += time;
                    nightWorkAllowance = nightWorkAllowance.add(hourlyWage.multiply(BigDecimal.valueOf(time)).multiply(BigDecimal.valueOf(statusOfWork.getNote().getRate())));
                    break;
                case 휴일근로:
                    time = (int) ChronoUnit.HOURS.between(statusOfWork.getStartTime(), statusOfWork.getFinishTime());
                    holidayWorkAllowanceBasis += time;
                    holidayWorkAllowance = holidayWorkAllowance.add(hourlyWage.multiply(BigDecimal.valueOf(time)).multiply(BigDecimal.valueOf(statusOfWork.getNote().getRate())));
                    break;
                case 무급휴가:
                    unpaidLeave = unpaidLeave.add(hourlyWage.multiply(BigDecimal.valueOf(8)).multiply(BigDecimal.valueOf(statusOfWork.getNote().getRate())));
            }
        }

        salaryStatement.setYear(year);
        salaryStatement.setMonth(month);
        salaryStatement.setMember(member);
        salaryStatement.setCompany(company);
        salaryStatement.setCompanyMember(companyMember);
        salaryStatement.setLaborContract(laborContract);
        salaryStatement.setStatusOfWorks(statusOfWorks);
        salaryStatement.setName(member.getName());
        salaryStatement.setTeam(companyMember.getTeam());
        salaryStatement.setGrade(companyMember.getGrade());
        salaryStatement.setHourlyWage(hourlyWage);
        salaryStatement.setBasePay(basicSalary);
        salaryStatement.setOvertimePay(overtimePay);
        salaryStatement.setOvertimePayBasis(overtimePayBasis);
        salaryStatement.setNightWorkAllowance(nightWorkAllowance);
        salaryStatement.setNightWorkAllowanceBasis(nightWorkAllowanceBasis);
        salaryStatement.setHolidayWorkAllowance(holidayWorkAllowance);
        salaryStatement.setHolidayWorkAllowanceBasis(holidayWorkAllowanceBasis);
        salaryStatement.setUnpaidLeave(unpaidLeave);
        salaryStatement.setSalary(basicSalary.add(overtimePay.add(nightWorkAllowance).add(holidayWorkAllowance).add(unpaidLeave)));

        BigDecimal incomeTax = calculateTax(basicSalary);

        salaryStatement.setIncomeTax(incomeTax);
        salaryStatement.setNationalCoalition(calculateNationalCoalition(basicSalary));
        salaryStatement.setHealthInsurance(calculateHealthInsurance(basicSalary));
        salaryStatement.setEmploymentInsurance(calculateEmploymentInsurance(basicSalary));
        salaryStatement.setTotalSalary(salaryStatement.getSalary().subtract(incomeTax).subtract(salaryStatement.getNationalCoalition()).subtract(salaryStatement.getHealthInsurance()).subtract(salaryStatement.getEmploymentInsurance()));
        salaryStatement.setBankName(laborContract.getBankName());
        salaryStatement.setAccountNumber(laborContract.getAccountNumber());
        salaryStatement.setAccountHolder(laborContract.getAccountHolder());
        return salaryStatement;
    }
}
