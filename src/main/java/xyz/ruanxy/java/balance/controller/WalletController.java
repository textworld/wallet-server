package xyz.ruanxy.java.balance.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ruanxy.java.balance.model.Wallet;
import xyz.ruanxy.java.balance.payload.WalletDto;
import xyz.ruanxy.java.balance.service.WalletService;

import java.text.ParseException;

@RestController
@RequestMapping(value = "/wallet")
public class WalletController {

    private final static Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WalletService walletService;

//    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
//    public void getWallets(@CurrentUser UserPrincipal currentUser,
//                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size ) {
//        Page<Wallet> walletPage = walletService.getAllWallets(currentUser, page, size);
//        List<WalletDto> dtos = walletPage.getContent().stream().map(wallet -> convertToDto(wallet)).collect(Collectors.toList());
//
//    }

    public WalletDto convertToDto(Wallet wallet) throws ParseException {
        WalletDto dto = modelMapper.map(wallet, WalletDto.class);
        return dto;
    }

    @PostMapping()
    public WalletDto create(@RequestBody WalletDto dto) {
        logger.info("walletdto: {}", dto);
        return walletService.create(dto);
    }


}
