package seckill.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seckill.example.dao.SeckillDao;
import seckill.example.entity.Seckill;
import seckill.example.service.SeckillService;

import java.util.List;

/**
 * Created by wang on 17-6-6.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillDao seckillDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,9);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }
}
