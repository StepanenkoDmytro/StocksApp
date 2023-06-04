package com.stock.rest;

import com.stock.dto.accountDtos.DepositDto;
import com.stock.dto.accountDtos.NewAccountDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.model.user.User;
import com.stock.service.AccountService;
import com.stock.service.UserService;
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

    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
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

//зробити цю функцію пізніше
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
