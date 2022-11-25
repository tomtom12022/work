package com.work.app.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.work.app.dao.CoinDao;
import com.work.app.dao.CoinRepository;
import com.work.app.object.CoinObjectJPA;

@Repository
public class CoinDaoImpl implements CoinDao {

	@Autowired
	private CoinRepository coinRespository;

	@Override
	public void insertCoin(CoinObjectJPA coinObjectJPA) throws Exception {
		String systemTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
		coinObjectJPA.setUpdateTime(systemTime);
		coinRespository.save(coinObjectJPA);
	}

	@Override
	public CoinObjectJPA updateCoin(CoinObjectJPA coinObjectJPA) throws Exception {
		String systemTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

		CoinObjectJPA updateData = coinRespository.findByCoinName(coinObjectJPA.getCoinName());
		updateData.setCoinName(coinObjectJPA.getCoinName());
		updateData.setCoinTWName(coinObjectJPA.getCoinTWName());
		updateData.setExchangeRate(coinObjectJPA.getExchangeRate());
		updateData.setUpdateTime(systemTime);
		updateData = coinRespository.save(updateData);
		return updateData;
	}

	@Override
	public List<CoinObjectJPA> selectCoin(List<String> coinNameItems) throws Exception {
		List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
		if (coinNameItems == null) {
			listCoinObjectJPA = coinRespository.findAll();
		} else {
			listCoinObjectJPA = coinRespository.findByCoinNameIn(coinNameItems);
		}

		return listCoinObjectJPA;
	}

	@Override
	public void deleteCoin(String coinName) throws Exception {
		coinRespository.deleteByCoinName(coinName);
	}

}
