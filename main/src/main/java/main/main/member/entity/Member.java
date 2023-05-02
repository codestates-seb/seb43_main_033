package main.main.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String residentNumber;
    private String grade;
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

//    private List<Company> companies = new ArrayList<>();
//
//    public void addCompany (Company company) {
//        this.companies.add(company);
//    }

}
