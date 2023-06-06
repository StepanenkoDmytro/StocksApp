package com.stock.rest;

import com.stock.dto.accountDtos.DepositDto;
import com.stock.dto.accountDtos.NewAccountDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.forCharts.PieCoinPrice;
import com.stock.dto.forCharts.PieCoinsData;
import com.stock.model.user.User;
import com.stock.service.AccountService;
import com.stock.service.CoinService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account/")
@CrossOrigin
public class AccountController {
    private final UserService userService;
    private final AccountService accountService;
    private final CoinService coinService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService, CoinService coinService) {
        this.userService = userService;
        this.accountService = accountService;
        this.coinService = coinService;
    }

    @PostMapping("create")
    public ResponseEntity createAccount(@RequestBody NewAccountDto accountDto,
                                        @AuthenticationPrincipal UserDetails userDetails){
        //спитати в Алекса як краще, повернути юзера з новим списком аккаунтів, або просто новий аккаунт
        User user = userService.getUserByUsername(userDetails.getUsername());
        accountService.createAccount(accountDto.getNewAccountName(), user);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("deposit")
    @Transactional
    public ResponseEntity<AccountDto> deposit(@RequestBody DepositDto deposit,
                                  @AuthenticationPrincipal UserDetails userDetails){
        AccountDto accountDto = accountService.depositToAccountById(deposit.getAccountId(), BigDecimal.valueOf(deposit.getDepositAmount()));
        //дописати методу accountService.depositToAccountById якийсь exception, щоб користувач дізнався про помилку
        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("/price-for-list")
    public ResponseEntity getPieCoinPrice(@RequestBody AccountDto account) {
//        Account account = accountService.getAccountById(accountID);
        List<PieCoinPrice> priceCoins = coinService.getPriceCoinsByList(account.getCoins());
        BigDecimal totalBalance = priceCoins.stream()
                .map(PieCoinPrice::getValue)
                .reduce(account.getBalance(), BigDecimal::add)
                .setScale(0, RoundingMode.HALF_UP);
        return ResponseEntity.ok(new PieCoinsData(priceCoins, totalBalance));
    }

// зробити цю функцію пізніше
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String addPhoto(@AuthenticationPrincipal UserDetails userDetails,
//                           @RequestParam("file") MultipartFile file,
//                           Model model) {
//        String email = userDetails.getUsername();
//        User user = userService.getUserByEmail(email);
//
//        try {
//            userService.saveUser(user, file);
//        } catch (IOException e) {
//            userService.saveUser(user);
//            throw new ImageNotFoundException("Image not saved");
//        }
//        return "redirect:/api/v1/user";
//    }
}
