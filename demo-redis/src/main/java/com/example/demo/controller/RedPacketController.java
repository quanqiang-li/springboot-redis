package com.example.demo.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.RedPacketInfo;
import com.example.demo.domain.RedPacketRecord;
import com.example.demo.mapper.RedPacketInfoMapper;
import com.example.demo.mapper.RedPacketRecordMapper;
import com.example.demo.service.RedisService;
/**
 * 抢红包
 * @author 2476056494@qq.com
 *
 */
@RestController
public class RedPacketController {


    @Autowired
    private RedisService redisService;

    @Autowired
    private RedPacketInfoMapper redPacketInfoMapper;

    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;

    private static final String TOTAL_NUM = "_totalNum";
    private static final String TOTAL_AMOUNT = "_totalAmount";

    /***
     *
     * @param uid
     * @param totalNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/addPacket")
    public String saveRedPacket(Integer uid, Integer totalNum, Integer totalAmount) {
        RedPacketInfo record = new RedPacketInfo();
        record.setUid(uid);
        record.setTotalAmount(totalAmount);
        record.setTotalPacket(totalNum);
        record.setCreateTime(new Date());
        record.setRemainingAmount(totalAmount);
        record.setRemainingPacket(totalNum);
        long redPacketId = System.currentTimeMillis();   //此时无法保证红包id唯一，最好是用雪花算法进行生成分布式系统唯一键
        record.setRedPacketId(redPacketId);
        redPacketInfoMapper.insert(record);
        redisService.set(redPacketId + TOTAL_NUM, totalNum + "");
        redisService.set(redPacketId + TOTAL_AMOUNT, totalAmount + "");
        return "success";
    }


    /**
     * 抢红包
     *
     * @param redPacketId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPacket")
    public Integer getRedPacket(long redPacketId) {
        String redPacketName = redPacketId + TOTAL_NUM;
        String num = (String) redisService.get(redPacketName);
        if (!StringUtils.isEmpty(num)) {
            return Integer.parseInt(num);
        }
        return 0;
    }


    /**
     * 拆红包
     *
     * @param redPacketId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRedPacketMoney")
    public String getRedPacketMoney(int uid, long redPacketId) {
        Integer randomAmount = 0;
        String redPacketName = redPacketId + TOTAL_NUM;
        String totalAmountName = redPacketId + TOTAL_AMOUNT;
        String num = (String) redisService.get(redPacketName);
        if (StringUtils.isEmpty(num) || Integer.parseInt(num) == 0) {
            return "抱歉！红包已经抢完了";
        }
        String totalAmount = (String) redisService.get(totalAmountName);
        if (!StringUtils.isEmpty(totalAmount)) {
            Integer totalAmountInt = Integer.parseInt(totalAmount);
            Integer totalNumInt = Integer.parseInt(num);
            Integer maxMoney = totalAmountInt / totalNumInt * 2;
            Random random = new Random();
            randomAmount = random.nextInt(maxMoney);
        }
        //课堂作业：lua脚本将这两个命令一起请求
        redisService.decr(redPacketName, 1);
        redisService.decr(totalAmountName,randomAmount);  //redis decreby功能
        updateRacketInDB(uid, redPacketId,randomAmount);
        return randomAmount + "";
    }

    public void updateRacketInDB(int uid, long redPacketId, int amount) {
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setUid(uid);
        redPacketRecord.setRedPacketId(redPacketId);
        redPacketRecord.setAmount(amount);
        redPacketRecord.setCreateTime(new Date());
        redPacketRecordMapper.insertSelective(redPacketRecord);
        //这里应该查出RedPacketInfo的数量，将总数量和总金额减去
    }


}
