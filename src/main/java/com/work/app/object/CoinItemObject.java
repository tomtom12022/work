package com.work.app.object;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CoinItemObject {
	
	private List<CoinObjectJPA> coinItem;
	
	private List<String> coinNameItems;
	
    public List<CoinObjectJPA> getCoinItem() {
        return coinItem;
    }

    public void setCoinItem(List<CoinObjectJPA> coinItem) {
        this.coinItem = coinItem;
    }

	public List<String> getCoinNameItems() {
		return coinNameItems;
	}

	public void setCoinNameItems(List<String> coinNameItems) {
		this.coinNameItems = coinNameItems;
	}
    
    
}