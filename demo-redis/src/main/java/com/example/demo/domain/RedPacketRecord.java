package com.example.demo.domain;

import java.util.Date;

public class RedPacketRecord {
    /**
     * 
     */
    private Integer id;

    /**
     * 抢到红包的金额
     */
    private Integer amount;

    /**
     * 抢到红包的用户的用户名
     */
    private String nickName;

    /**
     * 抢到红包的用户的头像
     */
    private String imgUrl;

    /**
     * 抢到红包用户的用户标识
     */
    private Integer uid;

    /**
     * 红包id，采用timestamp+5位随机数
     */
    private Long redPacketId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * get 
     */
    public Integer getId() {
        return id;
    }

    /**
     * set 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * get 抢到红包的金额
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * set 抢到红包的金额
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * get 抢到红包的用户的用户名
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * set 抢到红包的用户的用户名
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * get 抢到红包的用户的头像
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * set 抢到红包的用户的头像
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * get 抢到红包用户的用户标识
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * set 抢到红包用户的用户标识
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * get 红包id，采用timestamp+5位随机数
     */
    public Long getRedPacketId() {
        return redPacketId;
    }

    /**
     * set 红包id，采用timestamp+5位随机数
     */
    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    /**
     * get 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * set 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * get 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * set 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}