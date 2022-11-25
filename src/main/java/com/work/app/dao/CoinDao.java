package com.work.app.dao;

import java.util.List;

import com.work.app.object.CoinObjectJPA;

public interface CoinDao {

	public void insertCoin(CoinObjectJPA coinObjectJPA) throws Exception;

    public CoinObjectJPA updateCoin(CoinObjectJPA coinObjectJPA) throws Exception;

	public List<CoinObjectJPA> selectCoin(List<String> coinNameItems) throws Exception;

	public void deleteCoin(String coinName) throws Exception;

}
