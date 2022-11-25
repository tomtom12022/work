package com.work.app.object;

import java.util.Map;

public class CoinDeskObject {
	//時間
	TimeObject time;
	//免責聲明
	String disclaimer;
	//名稱
	String chartName;
	//Bpi
	Map<String,CoinObject> bpi;
	
	public TimeObject getTime() {
		return time;
	}
	public void setTime(TimeObject time) {
		this.time = time;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	public Map<String, CoinObject> getBpi() {
		return bpi;
	}
	public void setBpi(Map<String, CoinObject> bpi) {
		this.bpi = bpi;
	}
	
}
