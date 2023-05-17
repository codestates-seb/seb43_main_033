package main.main.salarystatement.mapper;

import main.main.company.entity.Company;
import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;
import main.main.salarystatement.dto.PreDto;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.salarystatement.entity.SalaryStatement;
import main.main.statusofwork.entity.StatusOfWork;
import org.mapstruct.Mapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SalaryStatementMapper {
    default SalaryStatement postToSalaryStatement(SalaryStatementDto.Post requestBody) {
        Member member = new Member();
        member.setMemberId(requestBody.getMemberId());
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        SalaryStatement salaryStatement = new SalaryStatement();
        salaryStatement.setMember(member);
        salaryStatement.setCompany(company);
        salaryStatement.setYear(requestBody.getYear());
        salaryStatement.setMonth(requestBody.getMonth());

        return salaryStatement;
    }

    default SalaryStatementDto.Response salaryStatementToResponse(SalaryStatement salaryStatement) {
        return SalaryStatementDto.Response.builder()
                .id(salaryStatement.getId())
                .companyId(salaryStatement.getCompany().getCompanyId())
                .companyName(salaryStatement.getCompany().getCompanyName())
                .memberId(salaryStatement.getMember().getMemberId())
                .year(salaryStatement.getYear())
                .month(salaryStatement.getMonth())
                .name(salaryStatement.getName())
                .team(salaryStatement.getTeam())
                .grade(salaryStatement.getGrade())
                .hourlyWage(salaryStatement.getHourlyWage())
                .basePay(salaryStatement.getBasePay())
                .overtimePay(salaryStatement.getOvertimePay())
                .overtimePayBasis(salaryStatement.getOvertimePayBasis())
                .nightWorkAllowance(salaryStatement.getNightWorkAllowance())
                .nightWorkAllowanceBasis(salaryStatement.getNightWorkAllowanceBasis())
                .holidayWorkAllowance(salaryStatement.getHolidayWorkAllowance())
                .holidayWorkAllowanceBasis(salaryStatement.getHolidayWorkAllowanceBasis())
                .unpaidLeave(salaryStatement.getUnpaidLeave())
                .salary(salaryStatement.getSalary())
                .incomeTax(salaryStatement.getIncomeTax())
                .nationalCoalition(salaryStatement.getNationalCoalition())
                .healthInsurance(salaryStatement.getHealthInsurance())
                .employmentInsurance(salaryStatement.getEmploymentInsurance())
                .totalSalary(salaryStatement.getTotalSalary())
                .bankName(salaryStatement.getBankName())
                .accountNumber(salaryStatement.getAccountNumber())
                .paymentStatus(salaryStatement.isPaymentStatus()).build();
    }

    default SalaryStatementDto.PreContent preContent(SalaryStatement salaryStatement, Boolean check, SalaryStatement checkSalaryStatement) {
        SalaryStatementDto.PreContent preContent = SalaryStatementDto.PreContent.builder()
                .member(preMember(salaryStatement))
                .status(preStatusList(salaryStatement))
                .statement(preStatement(salaryStatement))
                .exist(check).build();

        if(checkSalaryStatement != null) {
            preContent.setSalaryStatementId(checkSalaryStatement.getId());
        }

        return preContent;
    }

    default PreDto.Member preMember(SalaryStatement salaryStatement) {
        CompanyMember companyMember = salaryStatement.getCompanyMember();
        Member member = companyMember.getMember();

        return PreDto.Member.builder()
                .companyMemberId(companyMember.getCompanyMemberId())
                .name(member.getName())
                .bankName(salaryStatement.getBankName())
                .accountNumber(salaryStatement.getAccountNumber())
                .accountHolder(salaryStatement.getAccountHolder())
                .build();
    }

    default List<PreDto.Status> preStatusList(SalaryStatement salaryStatement) {
        return salaryStatement.getStatusOfWorks().stream()
                .sorted(Comparator.comparing(StatusOfWork::getId).reversed())
                .map(statusOfWork -> preStatus(statusOfWork))
                .collect(Collectors.toList());
    }

    default PreDto.Status preStatus(StatusOfWork statusOfWork) {
        return PreDto.Status.builder()
                .statusId(statusOfWork.getId())
                .startTime(statusOfWork.getStartTime())
                .finishTime(statusOfWork.getFinishTime())
                .note(statusOfWork.getNote().getStatus()).build();
    }

    default PreDto.Statement preStatement(SalaryStatement salaryStatement) {
        return PreDto.Statement.builder()
                .year(salaryStatement.getYear())
                .month(salaryStatement.getMonth())
                .basePay(salaryStatement.getBasePay())
                .overtimePay(salaryStatement.getOvertimePay())
                .nightWorkAllowance(salaryStatement.getNightWorkAllowance())
                .holidayWorkAllowance(salaryStatement.getHolidayWorkAllowance())
                .unpaidLeave(salaryStatement.getUnpaidLeave())
                .salary(salaryStatement.getSalary())
                .incomeTax(salaryStatement.getIncomeTax())
                .nationalCoalition(salaryStatement.getNationalCoalition())
                .healthInsurance(salaryStatement.getHealthInsurance())
                .employmentInsurance(salaryStatement.getEmploymentInsurance())
                .deduction(salaryStatement.getSalary().subtract(salaryStatement.getTotalSalary()))
                .totalSalary(salaryStatement.getTotalSalary()).build();
    }
}
