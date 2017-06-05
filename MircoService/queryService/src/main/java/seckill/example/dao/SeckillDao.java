package seckill.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import seckill.example.entity.Seckill;

import java.util.List;

/**
 * Created by wang on 17-6-6.
 */
@Mapper
public interface SeckillDao {
    /**
     * 根据id查询秒杀的商品信息
     * @param seckillId
     * @return
     */
    @Select("SELECT * FROM seckill WHERE seckill_id=#{seckillId}")
    Seckill queryById(@Param("seckillId") long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    @Select("SELECT * FROM seckill ORDER BY create_time DESC limit #{offset},#{limit}")
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
