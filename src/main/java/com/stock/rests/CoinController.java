package com.stock.rests;

import com.stock.dto.CoinDto;
import com.stock.api.entity.Coin;
import com.stock.service.CoinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coin")
public class CoinController {
    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

//    @GetMapping("/")
//    public List<CoinDto> getList(){
//        List<Coin> coins = coinService.getAllCoins();
//        return coins.stream().map(CoinDto::mapCoinToDto).collect(Collectors.toList());
//    }


}
