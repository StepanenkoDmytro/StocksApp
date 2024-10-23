package com.stock.model.user.account;

import com.stock.dto.accountDtos.*;
import com.stock.dto.forCharts.PieChartData;
import com.stock.model.user.User;
import com.stock.model.user.service.UserService;
import com.stock.model.user.account.service.AccountService;
import com.stock.model.coin.service.CoinService;
import com.stock.model.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account/")
@CrossOrigin
public class AccountController {
    private final UserService userService;
    private final AccountService accountService;
    private final CoinService coinService;
    private final StockService stockService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService, CoinService coinService, StockService stockService) {
        this.userService = userService;
        this.accountService = accountService;
        this.coinService = coinService;
        this.stockService = stockService;
    }

    @PostMapping("create")
    public ResponseEntity createAccount(@RequestBody NewAccountDto accountDto,
                                        @AuthenticationPrincipal UserDetails userDetails){
        //спитати в Алекса як краще, повернути юзера з новим списком аккаунтів, або просто новий аккаунт
        User user = userService.getFullUserByEmail(userDetails.getUsername());
        accountService.createAccount(accountDto.getNewAccountName(), accountDto.getAccountType(), user);
        return ResponseEntity.ok(UserDto.mapUserToUserDto(user));
    }

    @PostMapping("deposit")
    @Transactional
    public ResponseEntity<AccountDto> deposit(@RequestBody DepositDto deposit,
                                  @AuthenticationPrincipal UserDetails userDetails){
        AccountDto accountDto = accountService.depositToAccountById(deposit.getAccountID(), BigDecimal.valueOf(deposit.getDepositAmount()));
        //дописати методу accountService.depositToAccountById якийсь exception, щоб користувач дізнався про помилку
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/{accountID}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountID) {
        AccountDto account = AccountDto.mapAccount(accountService.getAccountById(accountID));
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("/pie-chart-data")
    public ResponseEntity<PieChartData> getPieCoinPrice(@RequestBody AccountDto account) {
        PieChartData pieChartData = accountService.getPieChartData(account);
        return ResponseEntity.ok(pieChartData);
    }

    @PostMapping("/actual-prices-data")
    public ResponseEntity<ActualPricesData> getActualPricesData(@RequestBody AccountDto account) {
        ActualPricesData actualPricesData = accountService.getActualPricesData(account);
        return ResponseEntity.ok(actualPricesData);
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
