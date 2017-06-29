package seckill.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seckill.example.dao.BalanceDao;
import seckill.example.dao.TransactionDetailDao;
import seckill.example.exception.InsufficientBalance;
import seckill.example.service.BalanceService;

/**
 * Created by wang on 17-6-26.
 */
@Service
public class BalanceServiceImpl implements BalanceService{
    @Autowired
    TransactionDetailDao transactionDetailDao;
    @Autowired
    BalanceDao balanceDao;

    @Transactional
    public Integer deductBalances(long userPhone,long goodsId, long price){
        //判断用户余额是否大于商品价格
        long balances = balanceDao.getBalances(userPhone);
        if(balances<price) {
            throw new InsufficientBalance("余额不足");
        }
        int deductUpdateCount = balanceDao.deductBalances(userPhone, price);
        int transactionUpdateCount = transactionDetailDao.addTransactionDetail(userPhone, goodsId, price);
        if (deductUpdateCount >= 1 && transactionUpdateCount >= 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
