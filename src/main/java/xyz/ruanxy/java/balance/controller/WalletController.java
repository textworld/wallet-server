package xyz.ruanxy.java.balance.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ruanxy.java.balance.model.Wallet;
import xyz.ruanxy.java.balance.payload.PagedResponse;
import xyz.ruanxy.java.balance.payload.WalletDto;
import xyz.ruanxy.java.balance.security.CurrentUser;
import xyz.ruanxy.java.balance.security.UserPrincipal;
import xyz.ruanxy.java.balance.service.WalletService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/wallet")
@CrossOrigin
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

    @GetMapping
    public PagedResponse<WalletDto> getAll(@CurrentUser UserPrincipal user, @RequestParam(name = "type") String type){
        logger.info("Current user {}", user.getUsername());
        return walletService.getAllWallets(user, type,0, 10);
    }

    @PostMapping
    public WalletDto create(@CurrentUser UserPrincipal user, @RequestBody WalletDto dto) {
        logger.info("walletdto: {}", dto);
        return walletService.create(user, dto);
    }

    @GetMapping(value = "{wallet}")
    public WalletDto get(@PathVariable String wallet, @CurrentUser UserPrincipal user){
        return walletService.get(user, wallet);
    }

    @DeleteMapping(value = "{wallet}")
    public ResponseEntity<?> delete(@PathVariable String wallet, @CurrentUser UserPrincipal user){
        walletService.delete(user, wallet);
        return ResponseEntity.ok("success");
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @CurrentUser UserPrincipal user, @RequestBody WalletDto dto){
        walletService.update(user, id, dto);
        return ResponseEntity.ok("success");
    }


}
