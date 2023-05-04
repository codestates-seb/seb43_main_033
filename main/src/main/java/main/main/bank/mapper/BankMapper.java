package main.main.bank.mapper;

import main.main.bank.dto.BankDto;
import main.main.bank.entity.Bank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {

    default BankDto.Response bankToBankResponse(Bank bank) {
        return BankDto.Response.builder()
                .bankId(bank.getBankId())
                .bankName(bank.getBankGroup().getBankName())
                .build();
    }

//    default BankDto.ResponseForList banksToBanksResponse(Bank.BankGroup bankGroup) {
//        return BankDto.ResponseForList.builder()
//                .bankName(bankGroup.getBankName())
//                .build();

    }

//    default List<BankDto.ResponseForList> banksForList(List<Bank> bankList) {
//        return bankList.stream()
//                .map( -> bankToBankResponse())
//                .collect(Collectors.toList());
//    }


