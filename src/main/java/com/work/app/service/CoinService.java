package com.work.app.service;

import java.io.IOException;
import java.util.List;

import com.work.app.object.CoinDeskObject;
import com.work.app.object.CoinObjectJPA;
import com.work.app.object.NewCoinApiObject;

public interface CoinService {

	public CoinDeskObject callCoinDeskApi() throws IOException;

	public NewCoinApiObject tranformDeskApi() throws IOException;

	public void insertCoin(List<CoinObjectJPA> coinItem) throws Exception;

	public List<CoinObjectJPA> updateCoin(List<CoinObjectJPA> coinItem) throws Exception;

	public List<CoinObjectJPA> selectCoin(List<String> coinNameItems) throws Exception;

	public void deleteCoin(List<String> coinNameItems) throws Exception;
}
