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
        double hourlyWage = laborContract.getBasicSalary() / 209L;
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
        salaryStatement.setBasePay(laborContract.getBasicSalary());
        salaryStatement.setOvertimePay(overtimePay);
        salaryStatement.setOvertimePayBasis(overtimePayBasis);
        salaryStatement.setNightWorkAllowance(nightWorkAllowance);
        salaryStatement.setNightWorkAllowanceBasis(nightWorkAllowanceBasis);
        salaryStatement.setHolidayWorkAllowance(holidayWorkAllowance);
        salaryStatement.setHolidayWorkAllowanceBasis(holidayWorkAllowanceBasis);
        salaryStatement.setSalary();

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
