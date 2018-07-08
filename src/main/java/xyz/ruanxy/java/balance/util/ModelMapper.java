package xyz.ruanxy.java.balance.util;

import xyz.ruanxy.java.balance.model.Wallet;
import xyz.ruanxy.java.balance.payload.WalletDto;

public class ModelMapper {
    public static Wallet marshal(WalletDto dto){
        Wallet model = new Wallet();
        model.setName(dto.getName());
        model.setAlias(dto.getAlias());
        model.setComment(dto.getComment());
        model.setType(dto.getType());
        model.setBalance(dto.getBalance());
        return model;
    }

    public static WalletDto unmarshal(Wallet model){
        WalletDto dto = new WalletDto();
        dto.setName(model.getName());
        dto.setAlias(model.getAlias());
        dto.setComment(model.getComment());
        dto.setType(model.getType());
        dto.setBalance(model.getBalance());

        return dto;
    }
}
