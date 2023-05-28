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
    @Column(name = "ID")
    private Long bankId;

    @Enumerated(EnumType.STRING)
    @Getter
    @Column(name = "bankname")
    private BankGroup bankGroup;

    @Getter
    @RequiredArgsConstructor
    public enum BankGroup {
        산업("002", "KDB산업은행"),
        기업("003", "IBK기업은행"),
        국민("004", "KB국민은행"),
        수협("007", "수협은행"),
        NH농협("011", "NH농협은행"),
        농협중앙회("012", "농협중앙회(단위농축협)"),
        우리("020", "우리은행"),
        SC제일("023", "SC제일은행"),
        한국씨티("027", "한국씨티은행"),
        대구("031", "대구은행"),
        부산("032", "부산은행"),
        광주("034", "광주은행"),
        제주("035", "제주은행"),
        전북("037", "전북은행"),
        경남("039", "경남은행"),
        새마을금고중앙회("045", "새마을금고중앙회"),
        신협중앙회("048", "신협중앙회"),
        저축은행중앙회("050", "저축은행중앙회"),
        산림조합중앙회("064", "산림조합중앙회"),
        우체국("071", "우체국"),
        하나("081", "하나은행"),
        신한("088", "신한은행"),
        케이뱅크("089", "케이뱅크"),
        카카오뱅크("090", "카카오뱅크"),
        토스뱅크("092", "토스뱅크"),
        KB증권("218", "KB증권"),
        미래에셋대우("238", "미래에셋대우"),
        삼성증권("240", "삼성증권"),
        한국투자증권("243", "한국투자증권"),
        NH투자증권("247", "NH투자증권"),
        교보증권("261", "교보증권"),
        하이투자증권("262", "하이투자증권"),
        현대차증권("263", "현대차증권"),
        키움증권("264", "키움증권"),
        이베스트투자증권("265", "이베스트투자증권"),
        SK증권("266", "SK증권"),
        대신증권("267", "대신증권"),
        한화투자증권("269", "한화투자증권"),
        토스증권("271", "토스증권"),
        신한금융투자("278", "신한금융투자"),
        DB금융투자("279", "DB금융투자"),
        유진투자증권("280", "유진투자증권"),
        메리츠증권("287", "메리츠증권");


        @Getter
        private String bankCode;

        @Getter
        private String bankName;


        BankGroup(String bankCode, String bankName) {

            this.bankCode = bankCode;
            this.bankName = bankName;
        }

        public String getBankCode() {
            return bankCode;
        }
        @JsonValue
        public String getBankName() {
            return bankName;
        }

        @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
        private List<BankGroup> bankList = new ArrayList<>();
    }
}