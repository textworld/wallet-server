package xyz.ruanxy.java.balance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.config.AppConstants;
import xyz.ruanxy.java.balance.exception.BadRequestException;
import xyz.ruanxy.java.balance.exception.ResourceNotFoundException;
import xyz.ruanxy.java.balance.model.User;
import xyz.ruanxy.java.balance.model.Wallet;
import xyz.ruanxy.java.balance.model.WalletType;
import xyz.ruanxy.java.balance.payload.PagedResponse;
import xyz.ruanxy.java.balance.payload.WalletDto;
import xyz.ruanxy.java.balance.repository.UserRepository;
import xyz.ruanxy.java.balance.repository.WalletRepository;
import xyz.ruanxy.java.balance.security.UserPrincipal;
import xyz.ruanxy.java.balance.util.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public PagedResponse<WalletDto> getAllWallets(UserPrincipal userPrincipal, String type, int page, int size){
        validatePageNumberAndSize(page, size);

        User user = userRepository.getOne(userPrincipal.getId());

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Wallet example = new Wallet();
        example.setType(WalletType.valueOf(type));
        example.setUser(user);

        Example<Wallet> e = Example.of(example);
        Page<Wallet> wallets = walletRepository.findAll(e, pageable);

        logger.info("UserId {}, wallets.getNumberOfElements() {}", userPrincipal.getId(), wallets.getNumberOfElements());

        if (wallets.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(),
                    wallets.getNumber(), wallets.getSize(), wallets.getTotalElements(),
                    wallets.getTotalPages(), wallets.isLast());
        }

        List<WalletDto> walletResponses = wallets.map(wallet -> {
            WalletDto dto = ModelMapper.unmarshal(wallet);
            dto.setId(wallet.getId());
            dto.setCreationDateTime(wallet.getCreatedAt());
            return dto;
        }).getContent();

        return new PagedResponse<>(walletResponses, wallets.getNumber(), wallets.getSize(),
                wallets.getTotalElements(), wallets.getTotalPages(), wallets.isLast());
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public WalletDto create(UserPrincipal userPrincipal, WalletDto dto){
        Wallet model = ModelMapper.marshal(dto);

        User user = userRepository.getOne(userPrincipal.getId());

        model.setUser(user);

        walletRepository.save(model);
        dto.setId(model.getId());
        return dto;
    }

    public WalletDto get(UserPrincipal userPrincipal, String walletName){
        User user = userRepository.getOne(userPrincipal.getId());

        Wallet wallet = new Wallet();
        wallet.setName(walletName);
        wallet.setUser(user);

        Example<Wallet> example = Example.of(wallet);

        Optional<Wallet> optional = walletRepository.findOne(example);

        Wallet w = optional.orElseThrow(() -> new ResourceNotFoundException("wallet", "name", walletName));

        WalletDto dto = ModelMapper.unmarshal(w);
        dto.setId(w.getId());

        return dto;
    }

    public Optional<Wallet> getOptional(UserPrincipal userPrincipal, String walletName){
        User user = userRepository.getOne(userPrincipal.getId());

        Wallet wallet = new Wallet();
        wallet.setName(walletName);
        wallet.setUser(user);

        Example<Wallet> example = Example.of(wallet);

        Optional<Wallet> optional = walletRepository.findOne(example);

        return optional;
    }

    public Optional<Wallet> getOptional(UserPrincipal userPrincipal, Long id){
        User user = userRepository.getOne(userPrincipal.getId());

        Wallet wallet = new Wallet();
        wallet.setId(id);
        wallet.setUser(user);

        Example<Wallet> example = Example.of(wallet);

        Optional<Wallet> optional = walletRepository.findOne(example);

        return optional;
    }

    public void delete(UserPrincipal userPrincipal, String walletName) {
        Optional<Wallet> optional = this.getOptional(userPrincipal, walletName);

        Wallet w = optional.orElseThrow(() -> new ResourceNotFoundException("wallet", "name", walletName));

        walletRepository.delete(w);
    }

    public void update(UserPrincipal userPrincipal, Long id, WalletDto dto){
        Optional<Wallet> optional = this.getOptional(userPrincipal, id);
        Wallet wallet = optional.orElseThrow(() -> new ResourceNotFoundException("wallet", "id", id));
        Wallet w = ModelMapper.marshal(dto);
        w.setId(wallet.getId());
        w.setUser(wallet.getUser());
        w.setCreatedAt(wallet.getCreatedAt());
        walletRepository.save(w);
    }
}
