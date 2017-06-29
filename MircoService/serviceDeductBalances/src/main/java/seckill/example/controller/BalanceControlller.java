package seckill.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import seckill.example.service.BalanceService;

/**
 * Created by wang on 17-6-26.
 */
@RestController
public class BalanceControlller {
    @Autowired
    BalanceService balanceService;
    @RequestMapping(value = "balances/{price}/{goodsId}",method = RequestMethod.PATCH)
    public Boolean deductBalances(@RequestParam("price") long price,@RequestParam("goodsId") long goodsId,@RequestParam("userPhone") long userPhone){
        int dedctCount = balanceService.deductBalances(userPhone,goodsId,price);
        if(dedctCount==1){
            return true;
        }
        return false;
    }
}
