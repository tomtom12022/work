package com.work.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.work.app.object.CoinDeskObject;
import com.work.app.object.CoinItemObject;
import com.work.app.object.CoinObjectJPA;
import com.work.app.object.NewCoinApiObject;
import com.work.app.object.Request;
import com.work.app.object.Response;
import com.work.app.service.CoinService;

@RestController
@RequestMapping("/Coin")
public class CoinController {

	@Autowired
	private CoinService coinService;

	/**
	 * 顯示CoinDeskAPI
	 * 
	 * @return Response<CoinDeskObject>
	 */
	@RequestMapping("/ShowCoinDeskApi")
	public @ResponseBody Response<CoinDeskObject> callCoinDeskApi() {
		Response<CoinDeskObject> response = new Response<CoinDeskObject>();
		try {
			CoinDeskObject coinDeskObject = coinService.callCoinDeskApi();
			response.setResponseCode("0000");
			response.setResponseMessage("SUCCESS");
			response.setResponseResult(coinDeskObject);
		} catch (Exception e) {
			response.setResponseCode("9999");
			response.setResponseMessage("取得API失敗");
		}
		return response;
	}

	/**
	 * 轉換API
	 * 
	 * @return Response<NewCoinApiObject>
	 */
	@RequestMapping("/TranformCoinDeskApi")
	public @ResponseBody Response<NewCoinApiObject> tranformCoinDeskApi() {
		Response<NewCoinApiObject> response = new Response<NewCoinApiObject>();
		try {
			NewCoinApiObject newCoinApiObject = coinService.tranformDeskApi();
			response.setResponseCode("0000");
			response.setResponseMessage("SUCCESS");
			response.setResponseResult(newCoinApiObject);
		} catch (Exception e) {
			response.setResponseCode("9999");
			response.setResponseMessage("轉換API失敗");
		}
		return response;

	}
	@PostMapping("/InsertCoin")
	public @ResponseBody Response<CoinItemObject> insertCoin(@RequestBody Request<CoinItemObject> request) {
		Response<CoinItemObject> response = new Response<CoinItemObject>();
		try {
			coinService.insertCoin(request.getRequestItem().getCoinItem());
			response.setResponseCode("0000");
			response.setResponseMessage("SUCCESS");
		} catch (Exception e) {
			response.setResponseCode("9999");
			response.setResponseMessage("新增幣別失敗");
		}
		return response;
	}

	@PutMapping("/UpdateCoin")
	public @ResponseBody Response<CoinItemObject> updateCoin(@RequestBody Request<CoinItemObject> request) {
		Response<CoinItemObject> response = new Response<CoinItemObject>();
		try {
			List<CoinObjectJPA> listCoinObjectJPA = coinService.updateCoin(request.getRequestItem().getCoinItem());
			CoinItemObject coinItemObject = new CoinItemObject();
			coinItemObject.setCoinItem(listCoinObjectJPA);
			response.setResponseResult(coinItemObject);
			response.setResponseCode("0000");
			response.setResponseMessage("SUCCESS");
		} catch (Exception e) {
			response.setResponseCode("9999");
			response.setResponseMessage("更新幣別失敗");
		}
		return response;
	}
	
	@PostMapping("/SelectCoin")
	public @ResponseBody Response<CoinItemObject> selectCoin(@RequestBody Request<CoinItemObject> request) {
		Response<CoinItemObject> response = new Response<CoinItemObject>();
		try {
			List<CoinObjectJPA> listCoinObjectJPA = coinService.selectCoin(request.getRequestItem().getCoinNameItems());
			CoinItemObject coinItemObject = new CoinItemObject();
			coinItemObject.setCoinItem(listCoinObjectJPA);
			response.setResponseResult(coinItemObject);
			response.setResponseCode("0000");
			response.setResponseMessage("SUCCESS");
		} catch (Exception e) {
			response.setResponseCode("9999");
			response.setResponseMessage("選擇幣別失敗");
		}
		return response;
	}
	
	@PostMapping("/DeleteCoin")
	public @ResponseBody Response<CoinItemObject> deleteCoin(@RequestBody Request<CoinItemObject> request) {
		Response<CoinItemObject> response = new Response<CoinItemObject>();
		try {
			coinService.deleteCoin(request.getRequestItem().getCoinNameItems());
			response.setResponseCode("0000");
			response.setResponseMessage("SUCCESS");
		} catch (Exception e) {
			response.setResponseCode("9999");
			response.setResponseMessage("刪除幣別失敗");
		}
		return response;
	}
}
