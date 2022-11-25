package com.work.app.object;

public class NewCoinObject {

	// 幣別英文
	String coinName;
	// 幣別中文名稱
	String coinTWName;
	// 匯率
	double exchangeRate;
	// 更新時間
	String updateTime;

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
