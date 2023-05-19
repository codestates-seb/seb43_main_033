package main.main.statusofwork.mapper;

import main.main.company.entity.Company;
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
        statusOfWork.setNote(requestBody.getNote());

        return statusOfWork;
    }

    default StatusOfWork postToStatusOfWork(StatusOfWorkDto.WPost requestBody) {
        StatusOfWork statusOfWork = new StatusOfWork();
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        statusOfWork.setCompany(company);
        statusOfWork.setStartTime(requestBody.getStartTime());
        statusOfWork.setFinishTime(requestBody.getFinishTime());
        statusOfWork.setNote(requestBody.getNote());

        return statusOfWork;
    }

    StatusOfWork patchToStatusOfWork(StatusOfWorkDto.Patch requestBody);

    default List<StatusOfWorkDto.Response> statusOfWorksToResponses(List<StatusOfWork> statusOfWorks) {
        return statusOfWorks.stream().map(statusOfWork -> statusOfWorkToResponse(statusOfWork)).collect(Collectors.toList());
    }

    default StatusOfWorkDto.Response statusOfWorkToResponse(StatusOfWork statusOfWork) {
        return StatusOfWorkDto.Response.builder()
                .id(statusOfWork.getId())
                .memberId(statusOfWork.getMember().getMemberId())
                .memberName(statusOfWork.getMember().getName())
                .companyId(statusOfWork.getCompany().getCompanyId())
                .companyName(statusOfWork.getCompany().getCompanyName())
                .startTime(statusOfWork.getStartTime())
                .finishTime(statusOfWork.getFinishTime())
                .note(statusOfWork.getNote().getStatus()).build();
    }

    default StatusOfWorkDto.MyWork statusOfWorkToMyWork(List<StatusOfWork> statusOfWorks) {
        return StatusOfWorkDto.MyWork.builder()
                .company(toCompanyInfoList(statusOfWorks.get(0).getMember().getCompanyMembers()))
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
                .companyName(companyMember.getCompany().getCompanyName()).build();
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
}
