package com.work.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.app.object.CoinObjectJPA;

public interface CoinRepository extends JpaRepository<CoinObjectJPA, Long> {
	/*
	@Transactional
	@Modifying
	@Query(value = "UPDATE TB_COIN SET COIN_TW_NAME = ?1 ,EXCHANGE_RATE = ?2 ,UPDATE_TIME = ?3 WHERE COIN_NAME = ?4 ;", nativeQuery = true)
	void updateCoin(String coinTWName,double exchangeRate,String updateTime,String coinName);
	*/
	public CoinObjectJPA findByCoinName(String coinName);

	public List<CoinObjectJPA> findByCoinNameIn(List<String> coinNameItems);

	public void deleteByCoinNameIn(List<String> coinNameItems);

	public void deleteByCoinName(String coinName);
}
