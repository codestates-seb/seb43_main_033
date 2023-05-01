package main.main.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String phoneNumber;
    private String email;
    private String residentNumber;
    private String grade;
    private String address;

//    private List<Company> companies = new ArrayList<>();
//
//    public void addCompany (Company company) {
//        this.companies.add(company);
//    }

}
