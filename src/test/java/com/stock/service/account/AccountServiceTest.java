package com.stock.service.account;

import com.stock.dto.accountDtos.AccountCoinDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.CoinDto;
import com.stock.exceptions.AccountFetchException;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.AccountType;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.service.TransactService;
import com.stock.service.UserService;
import com.stock.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactService transactService;
    @Mock
    private UserService userService;
    @InjectMocks
    private AccountServiceImpl accountService;

    private Account accountTest;
    private AccountCoin existingCoin;
    private CoinDto coinTest;

    @BeforeEach
    void init() {
        accountTest = new Account();
        accountTest.setId(1L);
        accountTest.setAccountType(AccountType.CryptoWallet);
        accountTest.setCoins(new ArrayList<>());

        coinTest = new CoinDto("btc", "Bitcoin", "BTC", BigDecimal.valueOf(20_000), BigDecimal.valueOf(20_000_000));

        existingCoin = AccountCoin.fromCoin(coinTest, BigDecimal.valueOf(200));
    }

    @Test
    @Tag("coinBuy")
    void processCoinBuyWithZeroBalance() {
        BigDecimal amount = BigDecimal.TEN;
        accountTest.setBalance(BigDecimal.ZERO);
        accountTest.setCoins(Collections.singletonList(existingCoin));

        BigDecimal expectedBalance = accountTest.getBalance().subtract(amount);
        BigDecimal expectedAmountUSD = existingCoin.getAmountUSD();
        AccountDto result = accountService.processCoinBuy(amount, coinTest, accountTest);
        AccountCoinDto resultCoin = result.getCoins().get(0);

        assertNotNull(result);
        assertNotEquals(expectedBalance, result.getBalance());
        assertEquals(1, result.getCoins().size());
        assertEquals(expectedAmountUSD, resultCoin.getAmountUSD());

        verify(transactService, times(1)).logCoinRejected(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactService, never()).logCoinSuccess(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
    }

    @Test
    @Tag("coinBuy")
    void processCoinBuyWithExistingCoin() {
        BigDecimal amount = BigDecimal.TEN;
        accountTest.setBalance(BigDecimal.TEN);
        accountTest.setCoins(Collections.singletonList(existingCoin));

        BigDecimal expectedBalance = accountTest.getBalance().subtract(amount);
        BigDecimal expectedAmountUSD = existingCoin.getAmountUSD().add(amount);

        AccountDto result = accountService.processCoinBuy(amount, coinTest, accountTest);
        AccountCoinDto resultCoin = result.getCoins().get(0);

        assertNotNull(result);
        assertEquals(1, result.getCoins().size());
        assertEquals(expectedBalance, result.getBalance());
        assertEquals(expectedAmountUSD, resultCoin.getAmountUSD());
        assertEquals(existingCoin.getAmountCOIN(), resultCoin.getAmountCOIN());

        verify(transactService, never()).logCoinRejected(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
        verify(transactService, times(1)).logCoinSuccess(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @Tag("coinBuy")
    void processCoinBuyAddNewCoin() {
        BigDecimal amount = BigDecimal.TEN;
        accountTest.setBalance(BigDecimal.TEN);

        AccountCoin accountCoinTest = AccountCoin.fromCoin(coinTest, amount);
        BigDecimal expectedBalance = accountTest.getBalance().subtract(amount);

        AccountDto result = accountService.processCoinBuy(amount, coinTest, accountTest);
        AccountCoinDto resultCoin = result.getCoins().get(0);

        assertNotNull(result);
        assertEquals(1, result.getCoins().size());
        assertEquals(expectedBalance, result.getBalance());
        assertEquals(amount, resultCoin.getAmountUSD());
        assertEquals(accountCoinTest.getAmountCOIN(), resultCoin.getAmountCOIN());

        verify(transactService, never()).logCoinRejected(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
        verify(transactService, times(1)).logCoinSuccess(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @Tag("coinBuy")
    void processCoinBuyAccountOrCoinNullThrowsException() {
        BigDecimal amount = BigDecimal.TEN;

        AccountFetchException exception = assertThrows(AccountFetchException.class, () -> accountService.processCoinBuy(amount, coinTest, null));
        assertEquals("In processCoinBuy account or coin is null", exception.getMessage());

        exception = assertThrows(AccountFetchException.class, () -> accountService.processCoinBuy(amount, null, accountTest));
        assertEquals("In processCoinBuy account or coin is null", exception.getMessage());

        verify(transactService, never()).logCoinSuccess(any(BigDecimal.class), any(AccountCoin.class), any(Account.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @Tag("createAccount")
    void createAccountSuccessfully() {
        String accountName = "Test Name";
        User user = new User();

        accountService.createAccount(accountName, user);
        List<Account> accountList = user.getAccounts();

        assertNotNull(user.getAccounts());
        assertEquals(1, accountList.size());
        assertEquals(accountName, accountList.get(0).getAccountName());
        assertNotNull(accountList.get(0).getAccountNumber());

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    @Tag("depositAccount")
    void depositAccountSuccessfully() {
        BigDecimal deposit = BigDecimal.TEN;
        accountTest.setBalance(BigDecimal.ZERO);

        when(accountRepository.findById(accountTest.getId())).thenReturn(Optional.of(accountTest));

        BigDecimal expectedBalance = accountTest.getBalance().add(deposit);
        AccountDto result = accountService.depositToAccountById(accountTest.getId(), deposit);

        assertEquals(expectedBalance, result.getBalance());

        verify(accountRepository, times(1)).findById(any(Long.class));
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(transactService, times(1)).logDepositSuccess(any(BigDecimal.class), any(Account.class));
    }
}
