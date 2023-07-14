package com.stock.rest;

import com.stock.dto.accountDtos.DepositDto;
import com.stock.dto.accountDtos.NewAccountDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.UserDto;
import com.stock.dto.forCharts.PiePrice;
import com.stock.dto.forCharts.PieData;
import com.stock.model.user.User;
import com.stock.service.AccountService;
import com.stock.service.CoinService;
import com.stock.service.StockService;
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


        User user = userService.getUserByUsername(userDetails.getUsername());
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

    @PostMapping("/price-for-list")
    public ResponseEntity<PieData> getPieCoinPrice(@RequestBody AccountDto account) {
        System.out.println(account);
        PieData pieData = null;
        if(account.getAccountType().equals("CryptoWallet")) {
            List<PiePrice> priceCoins = coinService.getPriceCoinsByList(account.getCoins());
            BigDecimal totalBalance = priceCoins.stream()
                    .map(PiePrice::getValue)
                    .reduce(account.getBalance(), BigDecimal::add)
                    .setScale(0, RoundingMode.HALF_UP);
            pieData = new PieData(priceCoins, totalBalance);
        } else if (account.getAccountType().equals("StockWallet")) {
            List<PiePrice> priceStocks = stockService.getPriceAccountStocksByList(account.getStocks());
            BigDecimal totalBalance = priceStocks.stream()
                    .map(PiePrice::getValue)
                    .reduce(account.getBalance(), BigDecimal::add)
                    .setScale(0, RoundingMode.HALF_UP);
            pieData = new PieData(priceStocks, totalBalance);
        }
        return ResponseEntity.ok(pieData);
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
