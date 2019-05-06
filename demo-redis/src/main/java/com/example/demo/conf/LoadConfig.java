package com.example.demo.conf;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.domain.UserScore;
import com.example.demo.mapper.UserScoreMapper;
import com.example.demo.service.RangingService;
import com.example.demo.service.RedisService;

@Component//必需的
public class LoadConfig implements InitializingBean{
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserScoreMapper userScoreMapper;
	@Autowired
	private RedisService redisService;

	@Override
	public void afterPropertiesSet() throws Exception {
		//Thread.sleep(1000 * 60);
		rankSaleAdd();
		log.info("导入销售积分到redis成功");
		
	}
	
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		rankSaleAdd();
//		log.info("导入销售积分到redis成功");
//	}
	
	/**
	 * 初始化数据,从mysql导入redis
	 */
    public void rankSaleAdd() {
        List<UserScore> userScores = userScoreMapper.selectAll();
        userScores.forEach(userScore -> {
            String value = userScore.getUserId() + ":" + userScore.getName();
            redisService.incrementScore(RangingService.SALESCORE, value, userScore.getUserScore());
        });
    }
}
