package com.example.demo.domain;

import java.util.Date;

public class RedPacketInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 红包id，采用timestamp+5位随机数
     */
    private Long redPacketId;

    /**
     * 红包总金额，单位分
     */
    private Integer totalAmount;

    /**
     * 红包总个数
     */
    private Integer totalPacket;

    /**
     * 剩余红包金额，单位分
     */
    private Integer remainingAmount;

    /**
     * 剩余红包个数
     */
    private Integer remainingPacket;

    /**
     * 新建红包用户的用户标识
     */
    private Integer uid;

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
     * get 红包总金额，单位分
     */
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * set 红包总金额，单位分
     */
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * get 红包总个数
     */
    public Integer getTotalPacket() {
        return totalPacket;
    }

    /**
     * set 红包总个数
     */
    public void setTotalPacket(Integer totalPacket) {
        this.totalPacket = totalPacket;
    }

    /**
     * get 剩余红包金额，单位分
     */
    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    /**
     * set 剩余红包金额，单位分
     */
    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    /**
     * get 剩余红包个数
     */
    public Integer getRemainingPacket() {
        return remainingPacket;
    }

    /**
     * set 剩余红包个数
     */
    public void setRemainingPacket(Integer remainingPacket) {
        this.remainingPacket = remainingPacket;
    }

    /**
     * get 新建红包用户的用户标识
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * set 新建红包用户的用户标识
     */
    public void setUid(Integer uid) {
        this.uid = uid;
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