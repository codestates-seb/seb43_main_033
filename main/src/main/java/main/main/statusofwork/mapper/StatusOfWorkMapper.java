package main.main.statusofwork.mapper;

import main.main.company.entity.Company;
import main.main.companymember.dto.CompanyMemberDto;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.dto.VacationDto;
import main.main.statusofwork.entity.RequestVacation;
import main.main.statusofwork.entity.StatusOfWork;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StatusOfWorkMapper {
    default StatusOfWork postToStatusOfWork(StatusOfWorkDto.Post requestBody) {
        StatusOfWork statusOfWork = new StatusOfWork();
        statusOfWork.setCompanyMember(new CompanyMember());
        statusOfWork.setCompany(new Company());
        statusOfWork.setStartTime(requestBody.getStartTime());
        statusOfWork.setFinishTime(requestBody.getFinishTime());
        if (requestBody.getNote() != null) {
            statusOfWork.setNote(requestBody.getNote());
        }

        return statusOfWork;
    }

    default StatusOfWork postToStatusOfWork(StatusOfWorkDto.WPost requestBody) {
        StatusOfWork statusOfWork = new StatusOfWork();
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        statusOfWork.setCompany(company);
        statusOfWork.setStartTime(requestBody.getStartTime());
        statusOfWork.setFinishTime(requestBody.getFinishTime());
        if (requestBody.getNote() != null) {
            statusOfWork.setNote(requestBody.getNote());
        }

        return statusOfWork;
    }

    StatusOfWork patchToStatusOfWork(StatusOfWorkDto.Patch requestBody);

    default List<StatusOfWorkDto.Response> statusOfWorksToResponses(List<StatusOfWork> statusOfWorks) {
        return statusOfWorks.stream().map(statusOfWork -> statusOfWorkToResponse(statusOfWork)).collect(Collectors.toList());
    }

    default StatusOfWorkDto.Response statusOfWorkToResponse(StatusOfWork statusOfWork) {
        return StatusOfWorkDto.Response.builder()
                .id(statusOfWork.getId())
                .memberId(statusOfWork.getCompanyMember().getMember().getMemberId())
                .memberName(statusOfWork.getCompanyMember().getMember().getName())
                .companyId(statusOfWork.getCompanyMember().getCompany().getCompanyId())
                .companyName(statusOfWork.getCompanyMember().getCompany().getCompanyName())
                .startTime(statusOfWork.getStartTime())
                .finishTime(statusOfWork.getFinishTime() != null ? statusOfWork.getFinishTime() : null)
                .note(statusOfWork.getNote().getStatus()).build();
    }

    default StatusOfWorkDto.MyWork statusOfWorkToMyWork(List<CompanyMember> companyMembers, List<StatusOfWork> statusOfWorks) {
        return StatusOfWorkDto.MyWork.builder()
                .company(toCompanyInfoList(companyMembers))
                .status(statusOfWorksToResponses(statusOfWorks)).build();
    }

    default List<StatusOfWorkDto.CompanyInfo> toCompanyInfoList(List<CompanyMember> companyMemberList) {
        return companyMemberList.stream()
                .map(companyMember -> toCompanyInfo(companyMember))
                .collect(Collectors.toList());
    }

    default StatusOfWorkDto.CompanyInfo toCompanyInfo(CompanyMember companyMember) {
        return StatusOfWorkDto.CompanyInfo.builder()
                .companyId(companyMember.getCompany().getCompanyId())
                .companyName(companyMember.getCompany().getCompanyName())
                .startTime(companyMember.getLaborContracts().get(0).getStartTime())
                .finishTime(companyMember.getLaborContracts().get(0).getFinishTime())
                .remainVacation(companyMember.getVacation().getCount()).build();
    }



    default RequestVacation postToRequestVacation(VacationDto.Post requestBody) {
        RequestVacation requestVacation = new RequestVacation();
        CompanyMember companyMember = new CompanyMember();
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        companyMember.setCompany(company);

        requestVacation.setVacationStart(requestBody.getVacationStart());
        requestVacation.setVacationEnd(requestBody.getVacationEnd());
        requestVacation.setCompanyMember(companyMember);

        return requestVacation;
    }

    default List<VacationDto.Response> requestResponses(List<RequestVacation> requestVacations) {
        return requestVacations.stream()
                .map(requestVacation -> requestResponse(requestVacation))
                .collect(Collectors.toList());
    }
    default VacationDto.Response requestResponse(RequestVacation requestVacation) {
        return VacationDto.Response.builder()
                .requestId(requestVacation.getId())
                .companyMemberId(requestVacation.getCompanyMember().getCompanyMemberId())
                .name(requestVacation.getCompanyMember().getMember().getName())
                .vacationStart(requestVacation.getVacationStart())
                .vacationEnd(requestVacation.getVacationEnd())
                .build();
    }

    default List<StatusOfWorkDto.Today> todayToResponse(List<CompanyMember> companyMembers) {
        return companyMembers.stream().map(statusOfWork -> toToday(statusOfWork)).collect(Collectors.toList());
    }

    default StatusOfWorkDto.Today toToday(CompanyMember companyMember) {
        return StatusOfWorkDto.Today.builder()
                .member(CompanyMemberDto.Response.builder()
                        .companyMemberId(companyMember.getCompanyMemberId())
                        .companyId(companyMember.getCompany().getCompanyId())
                        .memberId(companyMember.getMember().getMemberId())
                        .name(companyMember.getMember().getName())
                        .grade(companyMember.getGrade())
                        .team(companyMember.getTeam())
                        .status(companyMember.getStatus())
                        .roles(companyMember.getRoles())
                        .remainVacation(companyMember.getVacation().getCount()).build()
                )
                .status(statusOfWorksToResponses(companyMember.getStatusOfWorks())).build();
    }
}
