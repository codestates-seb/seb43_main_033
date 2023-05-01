package main.main.employer.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private String residentNumber;
    private String grade;
    private String address;

    private List<Company> companies = new ArrayList<>();

    public void addCompany (Company company) {
        this.companies.add(company);
    }

}
