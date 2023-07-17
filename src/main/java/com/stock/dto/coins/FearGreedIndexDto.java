package com.stock.dto.coins;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class FearGreedIndexDto {
    private int now;
    private int previousClose;
    private int oneWeekAgo;
    private int oneMonthAgo;
}
