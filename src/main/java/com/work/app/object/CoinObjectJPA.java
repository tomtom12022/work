package com.work.app.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "TB_COIN")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CoinObjectJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    // 幣別英文
    @Column(name = "COIN_NAME")
    private String coinName;

    // 幣別中文名稱
    @Column(name = "COIN_TW_NAME")
    private String coinTWName;

    // 匯率
    @Column(name = "EXCHANGE_RATE")
    private double exchangeRate;
    
    //更新時間
    @Column(name = "UPDATE_TIME")
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinTWName() {
        return coinTWName;
    }

    public void setCoinTWName(String coinTWName) {
        this.coinTWName = coinTWName;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
