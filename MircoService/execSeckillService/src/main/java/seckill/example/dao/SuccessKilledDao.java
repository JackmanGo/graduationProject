package seckill.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * Created by wang on 17-6-21.
 */
public interface SuccessKilledDao {
    /**
     * 减库存,执行时保证秒杀时间等均是对的
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新库存的记录行数
     */
    @Update("update seckill set number = number-1 where seckill_id=#{seckillId} AND start_time = #{killTime} AND end_time >= #{killTime} AND number > 0;")
    @ResultType(Integer.class)
    Integer reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return插入的行数
     */
    //当出现主键冲突时(即重复秒杀时)，会报错;不想让程序报错，加入ignore
    @Insert("INSERT ignore INTO success_killed(seckill_id, user_phone, state) VALUES (#{seckillId},#{userPhone},0)")
    @ResultType(Integer.class)
    Integer insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
