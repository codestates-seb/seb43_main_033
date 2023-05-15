package main.main.statusofwork.mapper;

import main.main.company.entity.Company;
import main.main.member.entity.Member;
import main.main.statusofwork.dto.StatusOfWorkDto;
import main.main.statusofwork.entity.StatusOfWork;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StatusOfWorkMapper {
    default StatusOfWork postToStatusOfWork(StatusOfWorkDto.Post requestBody) {
        Member member = new Member();
        member.setMemberId(requestBody.getMemberId());
        Company company = new Company();
        company.setCompanyId(requestBody.getCompanyId());
        StatusOfWork statusOfWork = new StatusOfWork();
        statusOfWork.setMember(member);
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
}
