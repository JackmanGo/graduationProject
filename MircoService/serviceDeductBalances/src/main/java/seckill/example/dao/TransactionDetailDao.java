package seckill.example.dao;

import org.apache.ibatis.annotations.*;

/**
 * Created by wang on 17-6-26.
 */
@Mapper
public interface TransactionDetailDao {
    /**
     * 增加购买明细
     * @param userPhone
     * @param goodsId
     * @param price
     * @return 如果影响行数=1，表示更新余额成功
     */
    @Insert("insert ignore into transaction_detail(user_phone,goodsId,beforeBalances,afterBalances)  select #{userPhone},#{goodsId},balances,balances-#{price} from user_info;")
    @ResultType(Integer.class)
    Integer addTransactionDetail(@Param("userPhone") long userPhone, @Param("goodsId") long goodsId,@Param("price") long price);

}
