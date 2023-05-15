package main.main.salarystatement.mapper;

import main.main.company.entity.Company;
import main.main.member.entity.Member;
import main.main.salarystatement.dto.SalaryStatementDto;
import main.main.salarystatement.entity.SalaryStatement;
import org.mapstruct.Mapper;

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
                .memberName(salaryStatement.getMember().getName())
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
}
