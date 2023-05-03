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
}

