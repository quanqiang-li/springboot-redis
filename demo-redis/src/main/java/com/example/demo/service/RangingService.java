package com.example.demo.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.example.demo.domain.UserScore;
import com.example.demo.mapper.UserScoreMapper;

@Service
public class RangingService {

	private static final String RANKGNAME = "user_score";

	public static final String SALESCORE = "sale_score_rank:";

	@Autowired
	private RedisService redisService;

//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private ScoreFlowMapper scoreFlowMapper;
//
    @Autowired
    private UserScoreMapper userScoreMapper;

	public void rankAdd(String uid, Integer score) {
		redisService.zAdd(RANKGNAME, uid, score);
	}

	public void increSocre(String uid, Integer score) {

		redisService.incrementScore(RANKGNAME, uid, score);
	}

	public Long rankNum(String uid) {
		return redisService.zRank(RANKGNAME, uid);
	}

	public Long score(String uid) {
		Long score = redisService.zSetScore(RANKGNAME, uid).longValue();
		return score;
	}

	/**
	 * 获取排名范围数据
	 * 
	 * @param start 排名开始位,从0开始计数
	 * @param end 排名结束位
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>> rankWithScore(Integer start, Integer end) {
		return redisService.zRankWithScore(RANKGNAME, start, end);
	}

//    public void rankSaleAdd() {
//        UserScoreExample example = new UserScoreExample();
//        example.setOrderByClause("id desc");
//        List<UserScore> userScores = userScoreMapper.selectByExample(example);
//        userScores.forEach(userScore -> {
//            String key = userScore.getUserId() + ":" + userScore.getName();
//            redisService.zAdd(SALESCORE, key, userScore.getUserScore());
//        });
//    }

	/**
	 * 添加用户积分
	 *
	 * @param uid
	 * @param score
	 */
    public void increSaleSocre(String uid,String name, Integer score) {
        int uidInt = Integer.parseInt(uid);
        long socreLong = Long.parseLong(score + "");
        String value = uid + ":" + name;
        //数据库只会新加一条记录,即同一个uid会有多条
        userScoreMapper.insertSelective(new UserScore(uidInt, socreLong, name));
        //redis发现有同样的value,会累加分数
        redisService.incrementScore(SALESCORE, value, score);
    }

    /**
     * 获取用户的排名和分数
     * @param uid
     * @param name
     * @return
     */
	public Map<String, Object> userRank(String uid, String name) {
		Map<String, Object> retMap = new LinkedHashMap<>();
		String value = uid + ":" + name;
		//排名
		Integer rank = redisService.zRank(SALESCORE, value).intValue();
		//分数
		Long score = redisService.zSetScore(SALESCORE, value).longValue();
		retMap.put("userId", uid);
		retMap.put("score", score);
		retMap.put("rank", rank);
		return retMap;
	}

	/**
	 * 有序集合获取排名,从高到低,按排名
	 * @param start 从0开始计数
	 * @param end
	 * @return
	 */
	public List<UserScore> reverseZRankWithRank(long start, long end) {
		Set<ZSetOperations.TypedTuple<Object>> setObj = redisService.reverseZRankWithRank(SALESCORE, start, end);
		/*
		 * List<Map<String, Object>> mapList = setObj.stream().map(objectTypedTuple -> {
		 * Map<String, Object> map = new LinkedHashMap<>(); map.put("userId",
		 * objectTypedTuple.getValue().toString().split(":")[0]); map.put("userName",
		 * objectTypedTuple.getValue().toString().split(":")[1]); map.put("score",
		 * objectTypedTuple.getScore()); return map; }).collect(Collectors.toList());
		 */
		List<UserScore> userScoreList = new ArrayList<>();
		setObj.forEach(typedTuple->{
			int uidInt = Integer.parseInt(typedTuple.getValue().toString().split(":")[0]);
			String name =typedTuple.getValue().toString().split(":")[1];
			long socreLong =typedTuple.getScore().longValue();
			UserScore us = new UserScore(uidInt, socreLong, name);
			userScoreList.add(us);
			});
		return userScoreList;
	}

	public List<Map<String, Object>> saleRankWithScore(Integer start, Integer end) {
		Set<ZSetOperations.TypedTuple<Object>> setObj = redisService.reverseZRankWithScore(SALESCORE, start, end);
		List<Map<String, Object>> mapList = setObj.stream().map(objectTypedTuple -> {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("userId", objectTypedTuple.getValue().toString().split(":")[0]);
			map.put("userName", objectTypedTuple.getValue().toString().split(":")[1]);
			map.put("score", objectTypedTuple.getScore());
			return map;
		}).collect(Collectors.toList());
		return mapList;
	}

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("======enter run bean=======");
//        Thread.sleep(100000);
//        this.rankSaleAdd();
//    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("======enter init bean=======");
//        this.rankSaleAdd();
//    }
}
