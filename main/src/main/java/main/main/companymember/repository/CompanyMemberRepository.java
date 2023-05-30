package main.main.companymember.repository;

import main.main.company.entity.Company;
import main.main.companymember.dto.Status;
import main.main.companymember.entity.CompanyMember;
import main.main.member.entity.Member;
import main.main.statusofwork.entity.StatusOfWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, Long> {
    CompanyMember findByMemberAndCompany(Member member, Company company);

    Page<CompanyMember> findAllByCompanyCompanyIdAndStatus(Long companyId, Status status, PageRequest status1);

    Page<CompanyMember> findAllByCompanyCompanyId(Long companyId, PageRequest status);

    Page<CompanyMember> findAllByStatusOfWorksNoteNotAndStatusOfWorksStartTimeBeforeAndStatusOfWorksFinishTimeAfter(
            StatusOfWork.Note note, LocalDateTime nowForStart, LocalDateTime nowForEnd, PageRequest pageRequest);

    List<CompanyMember> findAllByMemberMemberId(long memberId);

    boolean existsByCompanyAndMember(Company company, Member member);

    List<CompanyMember> findByMember_MemberId(Long memberId);
}
