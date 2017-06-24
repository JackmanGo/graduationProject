package seckill.example.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import seckill.example.dto.SeckillResult;
import seckill.example.entity.Seckill;
import seckill.example.entity.SuccessKilled;
import seckill.example.service.SeckillService;

import java.util.Date;
import java.util.List;

@RestController
public class SeckillController {
	private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
	@Autowired
	private DiscoveryClient client;
	@Autowired
	private SeckillService seckillService;
	/**
	 *查询某个详情商品
	 **/
	@RequestMapping(value = "/commodity/{seckillId}", method = RequestMethod.GET)
	public Seckill detail(@PathVariable("seckillId") Long seckillId) {
		logger.info("查询的seckillId为"+seckillId);
		logger.debug("test");
		if (seckillId == null) {
			logger.info("seckill为null");
		}

		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			logger.info("查询无结果");
		}
		return seckill;
	}
	/**
     *查询全部商品
     **/
	@RequestMapping(value = "commodities", method = RequestMethod.GET)
	public List<Seckill> list() {
		// list.jsp+mode=ModelAndView
		// 获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		return list;
	}
	/**
	 *查询秒杀成功结果
	 **/
	@RequestMapping(value = "/seckill/{seckilled}", method = RequestMethod.GET)
	public SuccessKilled successDetail(@PathVariable long seckillId,@RequestParam("userPhone")long userPhone) {
		return seckillService.getSuccessSeckill(seckillId,userPhone);
	}
	/**
     *获取当前时间
     **/
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	public SeckillResult<Long> time() {
		ServiceInstance instance = client.getLocalServiceInstance();
		Date now = new Date();
		logger.info(" service_id:" + instance.getServiceId());
		return new SeckillResult<Long>(true, now.getTime());
	}

}
