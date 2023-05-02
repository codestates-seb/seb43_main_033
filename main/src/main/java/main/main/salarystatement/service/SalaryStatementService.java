package main.main.salarystatement.service;

import lombok.RequiredArgsConstructor;
import main.main.company.entity.Company;
import main.main.company.service.CompanyService;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.laborcontract.entity.LaborContract;
import main.main.laborcontract.service.LaborContractService;
import main.main.member.entity.Member;
import main.main.member.service.MemberService;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.salarystatement.repository.SalaryStatementRepository;
import main.main.statusofwork.entity.StatusOfWork;
import main.main.statusofwork.service.StatusOfWorkService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static main.main.statusofwork.entity.StatusOfWork.note.연장근로;

@Service
@RequiredArgsConstructor
public class SalaryStatementService {
    private final SalaryStatementRepository salaryStatementRepository;
    private final MemberService memberService;
    private final CompanyService companyService;
    private final StatusOfWorkService statusOfWorkService;
    private final LaborContractService laborContractService;

    public void createSalaryStatement(SalaryStatement salaryStatement) {
        Member member = memberService.findMember(salaryStatement.getMember().getMemberId());
        Company company = companyService.findCompany(salaryStatement.getCompany().getCompanyId());
        List<StatusOfWork> statusOfWorks = statusOfWorkService.findStatusOfWorks(salaryStatement.getYear(), salaryStatement.getMonth(), member.getMemberId());
        LaborContract laborContract = laborContractService.findLaborContractForSalaryStatement(member, company, salaryStatement.getYear(), salaryStatement.getMonth());
        double basicSalary = laborContract.getBasicSalary();
        double hourlyWage = basicSalary / 209L;
        int time;
        double overtimePay = 0;
        int overtimePayBasis = 0;
        double nightWorkAllowance = 0;
        int nightWorkAllowanceBasis = 0;
        double holidayWorkAllowance = 0;
        int holidayWorkAllowanceBasis = 0;

        for (StatusOfWork statusOfWork : statusOfWorks) {
            switch (statusOfWork.getNote()) {
                case 연장근로:
                    time = (int) ChronoUnit.HOURS.between(statusOfWork.getStartTime(), statusOfWork.getFinishTime());
                    overtimePayBasis += time;
                    overtimePay = overtimePay + (statusOfWork.getNote().getRate() * hourlyWage * time);
                    break;
                case 야간근로:
                    time = (int) ChronoUnit.HOURS.between(statusOfWork.getStartTime(), statusOfWork.getFinishTime());
                    nightWorkAllowanceBasis += time;
                    nightWorkAllowance = nightWorkAllowance + (statusOfWork.getNote().getRate() * hourlyWage * time);
                    break;
                case 휴일근로:
                    time = (int) ChronoUnit.HOURS.between(statusOfWork.getStartTime(), statusOfWork.getFinishTime());
                    holidayWorkAllowanceBasis += time;
                    holidayWorkAllowance = holidayWorkAllowance + (statusOfWork.getNote().getRate() * hourlyWage * time);
                    break;
            }
        }

        salaryStatement.setMember(member);
        salaryStatement.setCompany(company);
        salaryStatement.setStatusOfWorks(statusOfWorks);
        salaryStatement.setHourlyWage(hourlyWage);
        salaryStatement.setBasePay(basicSalary);
        salaryStatement.setOvertimePay(overtimePay);
        salaryStatement.setOvertimePayBasis(overtimePayBasis);
        salaryStatement.setNightWorkAllowance(nightWorkAllowance);
        salaryStatement.setNightWorkAllowanceBasis(nightWorkAllowanceBasis);
        salaryStatement.setHolidayWorkAllowance(holidayWorkAllowance);
        salaryStatement.setHolidayWorkAllowanceBasis(holidayWorkAllowanceBasis);
        salaryStatement.setSalary();

        double taxBase = (basicSalary - (basicSalary * 0.0873)) * 12;
        double incomeTex;
        if (taxBase <= 14000000) {
            incomeTex = (taxBase * 0.06) / 12;
        } else if (taxBase <= 50000000) {
            incomeTex = (840000 + ((taxBase - 14000000) * 0.15)) / 12;
        } else if (taxBase <= 88000000) {
            incomeTex = (6240000 + ((taxBase - 50000000) * 0.24)) / 12;
        } else if (taxBase <= 150000000) {
            incomeTex = (15360000 + ((taxBase - 88000000) * 0.35)) / 12;
        } else if (taxBase <= 300000000) {
            incomeTex = (37060000 + ((taxBase - 150000000) * 0.38)) / 12;
        } else if (taxBase <= 500000000) {
            incomeTex = (94060000 + ((taxBase - 300000000) * 0.4)) / 12;
        } else if (taxBase <= 1000000000) {
            incomeTex = (174060000 + ((taxBase - 50000000) * 0.42)) / 12;
        } else {
            incomeTex = (384060000 + ((taxBase - 1000000000) * 0.45)) / 12;
        }

        salaryStatement.setIncomeTax(incomeTex);
        salaryStatement.setNationalCoalition(basicSalary * 0.045);
        salaryStatement.setHealthInsurance(basicSalary * 0.0343);
        salaryStatement.setEmploymentInsurance(basicSalary * 0.008);
        salaryStatement.setTotalSalary();

        SalaryStatement savedSalaryStatement = salaryStatementRepository.save(salaryStatement);
        statusOfWorks.stream().forEach(statusOfWork -> statusOfWork.setSalaryStatement(savedSalaryStatement));
    }

    public SalaryStatement findSalaryStatement(long salaryStatementId) {
        return  findVerifiedSalaryStatement(salaryStatementId);
    }

    public void deleteSalaryStatement(long salaryStatementId) {
        salaryStatementRepository.delete(findVerifiedSalaryStatement(salaryStatementId));
    }

    private SalaryStatement findVerifiedSalaryStatement(long salaryStatementId) {
        Optional<SalaryStatement> optionalSalaryStatement = salaryStatementRepository.findById(salaryStatementId);
        SalaryStatement salaryStatement = optionalSalaryStatement.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SALARYSTATEMENT_NOT_FOUND));

        return  salaryStatement;
    }
}
