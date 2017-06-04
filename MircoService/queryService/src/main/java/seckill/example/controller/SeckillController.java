package seckill.example.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import seckill.example.model.SeckillResult;

import java.util.Date;

@RestController
@RequestMapping("/seckill") // url:模块/资源/{}/细分
public class SeckillController {
	private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
	@Autowired
	private DiscoveryClient client;

    /*
	@RequestMapping(value = "/listGoods", method = RequestMethod.GET)
	public List<Seckill> list(HttpServletResponse response) {
		// list.jsp+mode=ModelAndView
		// 获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		response.setHeader("Access-Control-Allow-Origin", "*");
		return list;
	}

	@RequestMapping(value = "/detail/{seckillId}", method = RequestMethod.GET)
	public Seckill detail(@PathVariable("seckillId") Long seckillId, HttpServletResponse response) {
		logger.info("查询的seckillId为"+seckillId);
		logger.debug("test");
		if (seckillId == null) {
			logger.info("seckill为null");
		}

		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			logger.info("查询无结果");
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		return seckill;
	}

	// ajax ,json暴露秒杀接口的方法
	@RequestMapping(value = "/exposer/{seckillId}", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId, HttpServletResponse response) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			logger.info(exposer.toString());
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			e.printStackTrace();
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		return result;
	}

	@RequestMapping(value = "/execution/{seckillId}/{md5}", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long phones) {
		Long phone = Long.valueOf("18408221624");
		if (phone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		SeckillResult<SeckillExecution> result;

		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e1) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (SeckillCloseException e2) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}

	}

	*/
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	public SeckillResult<Long> time() {
		ServiceInstance instance = client.getLocalServiceInstance();
		Date now = new Date();
		logger.info(" service_id:" + instance.getServiceId());
		return new SeckillResult<Long>(true, now.getTime());
	}

}