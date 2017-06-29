package seckill.example.dao;

import org.apache.ibatis.annotations.*;

/**
 * Created by wang on 17-6-26.
 */
@Mapper
public interface BalanceDao {
    @Select("select balances from user_info where user_phone=#{userPhone};")
    @ResultType(Long.class)
    long getBalances(@Param("userPhone")  long userPhone);
    /**
     * 减余额
     * @param userPhone
     * @param price
     * @return 如果影响行数=1，表示更新余额成功
     */
    @Update("update user_info set balances = balances-#{price} where user_phone=#{userPhone};")
    @ResultType(Integer.class)
    Integer deductBalances(@Param("userPhone") long userPhone, @Param("price") long price);

}
