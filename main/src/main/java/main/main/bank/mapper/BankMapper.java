package main.main.bank.mapper;

import main.main.bank.dto.BankDto;
import main.main.bank.entity.Bank;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BankMapper {

    default BankDto.Response bankToBankResponse(Bank bank) {
        return BankDto.Response.builder()
                .bankId(bank.getBankId())
                .bankName(bank.getBankGroup().getBankName())
                .bankCode(bank.getBankGroup().getBankCode())
                .build();
    }

    default BankDto.ResponseForList bankToBankResponseForList(Bank bank) {
        return BankDto.ResponseForList.builder()
                .bankId(bank.getBankId())
                .bankName(bank.getBankGroup().getBankName())
                .bankCode(bank.getBankGroup().getBankCode())
                .build();
    }

    default List<BankDto.ResponseForList> banksToBanksResponse(List<Bank> banks) {
        return banks.stream()
                .map(bank -> bankToBankResponseForList(bank))
                .collect(Collectors.toList());
    }
}

