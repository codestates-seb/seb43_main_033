package main.main.bank.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long bankId;

    @Enumerated(EnumType.STRING)
    @Getter
    private BankGroup bankGroup;
    @Getter
    @RequiredArgsConstructor
    public enum BankGroup {
        국민("국민은행"),
        우리("우리은행"),
        신한("신한은행"),
        카카오("카카오뱅크"),
        농협("농협은행"),
        수협("수협은행");

        @Getter
        private String bankName;

        BankGroup(String bankName) {
            this.bankName = bankName;
        }

        @JsonValue
        public String getBankName() { return bankName; }


    @OneToMany(mappedBy = "bank")
    private List<BankGroup> bankList = new ArrayList<>();

//        @OneToMany(mappedBy = "bank")
//        private List<MemberBank> bankList = new ArrayList<>();



//    public void addBankList (BankGroup bankGroup) {
//        this.bankList.add(bankGroup);
//    }

    }
//    @OneToMany(mappedBy = "bank")
//    private List<MemberBank> bankList = new ArrayList<>();
//    public void addMemberBank(MemberBank memberBank) {
//        this.bankList.add(memberBank);
//        if(!memberBank.getBank().getBankGroup().equals(this)) {
//            memberBank.getBank().setBankGroup(this.getBankGroup());
//        }
//    }
}


//    public boolean hasBankName(String bank) {
//        return bankName != null && bankName.equals(bank);
//
//    }