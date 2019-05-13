package com.example.demo.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SeckillService {

    private static final String secStartPrefix = "skuId_start_";
    private static final String secAccess = "skuId_access_";
    private static final String secCount = "skuId_count_";
    private static final String filterName = "skuId_bloomfilter_";
    private static final String bookedName = "skuId_booked_";


    @Resource
    private RedisService redisService;

    public String seckill(int uid, int skuId) {
        //流量拦截层
        //1、判断秒杀是否开始   0_1554045087    开始标识_开始时间
        String isStart = (String) redisService.get(secStartPrefix + skuId);
        if (StringUtils.isEmpty(isStart)) {
            return "还未开始";
        }
        if (isStart.contains("_")) {
            Integer isStartInt = Integer.parseInt(isStart.split("_")[0]);
            Integer startTime = Integer.parseInt(isStart.split("_")[1]);
            if (isStartInt == 0) {
                if (startTime > getNow()) {
                    return "还未开始";
                } else {
                    //代表秒杀已经开始
                    redisService.set(secStartPrefix + skuId, 1+"");
                }
            } else {
                return "系统异常";
            }
        } else {
            if (Integer.parseInt(isStart) != 1) {
                return "系统异常";
            }
        }
        //流量拦截
        String skuIdAccessName = secAccess + skuId;
        Integer accessNumInt = 0;
        String accessNum = (String) redisService.get(skuIdAccessName);
        if(!StringUtils.isEmpty(accessNum)){
            accessNumInt = Integer.parseInt(accessNum);
        }
        String skuIdCountName = secCount + skuId;
        Integer countNumInt = Integer.parseInt((String) redisService.get(skuIdCountName));
        if (countNumInt * 1.2 < accessNumInt) {
            return "抢购已经完成,欢迎下次参与";
        } else {
            redisService.incr(skuIdAccessName);
        }
        //信息校验层
        if (redisService.bloomFilterExists(filterName, uid)){
            return "您已经抢购过该商品，请勿重复下发!";
        }else{
            redisService.bloomFilterAdd(filterName, uid);
        }
        Boolean isSuccess = redisService.getAndIncrLua(bookedName+skuId);
        if(isSuccess){
            return "恭喜您抢购成功！！！";
        }else{
            return "抢购结束,欢迎下次参与";
        }
    }

    private long getNow() {
        return System.currentTimeMillis() / 1000;
    }
}
