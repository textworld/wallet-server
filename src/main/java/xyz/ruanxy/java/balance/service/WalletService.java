package xyz.ruanxy.java.balance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.ruanxy.java.balance.config.AppConstants;
import xyz.ruanxy.java.balance.exception.BadRequestException;
import xyz.ruanxy.java.balance.model.User;
import xyz.ruanxy.java.balance.model.Wallet;
import xyz.ruanxy.java.balance.payload.PagedResponse;
import xyz.ruanxy.java.balance.payload.WalletDto;
import xyz.ruanxy.java.balance.repository.UserRepository;
import xyz.ruanxy.java.balance.repository.WalletRepository;
import xyz.ruanxy.java.balance.security.UserPrincipal;

import java.util.Collections;
import java.util.List;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public PagedResponse<WalletDto> getAllWallets(UserPrincipal userPrincipal, int page, int size){
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Wallet> wallets = walletRepository.findAll(pageable);

        logger.info("UserId {}, wallets.getNumberOfElements() {}", userPrincipal.getId(), wallets.getNumberOfElements());

        if (wallets.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(),
                    wallets.getNumber(), wallets.getSize(), wallets.getTotalElements(),
                    wallets.getTotalPages(), wallets.isLast());
        }

        List<WalletDto> walletResponses = wallets.map(wallet -> {
            WalletDto dto = new WalletDto();
            dto.setId(wallet.getId());
            dto.setName(wallet.getName());
            dto.setAlias(wallet.getAlias());
            dto.setComment(wallet.getComment());
            dto.setType(wallet.getType());
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
        Wallet model = new Wallet();
        model.setName(dto.getName());
        model.setAlias(dto.getAlias());
        model.setType(dto.getType());
        model.setComment(dto.getComment());

        User user = userRepository.getOne(userPrincipal.getId());

        model.setUser(user);

        walletRepository.save(model);
        dto.setId(model.getId());
        return dto;
    }
}
