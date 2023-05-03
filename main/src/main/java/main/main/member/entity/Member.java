package main.main.member.entity;

import lombok.*;
import main.main.company.entity.Company;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ManyToOne
    private Company company;

}