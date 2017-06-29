package seckill.example.service;

/**
 * Created by wang on 17-6-26.
 */
public interface BalanceService {
    public Integer deductBalances(long userPhone,long goodIds,long price);
}
