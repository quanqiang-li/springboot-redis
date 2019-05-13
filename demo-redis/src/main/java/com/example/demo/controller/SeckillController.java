package com.example.demo.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SeckillService;
/**
 * 秒杀
 * @author 2476056494@qq.com
 *
 */
@RestController
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    @RequestMapping("/redis/seckill")
    public String secKill(int uid,int skuId){
         return seckillService.seckill(uid,skuId);
    }
}
