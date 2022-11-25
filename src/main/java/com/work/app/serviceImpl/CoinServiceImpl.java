package com.work.app.serviceImpl;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.app.dao.CoinDao;
import com.work.app.object.CoinDeskObject;
import com.work.app.object.CoinObjectJPA;
import com.work.app.object.NewCoinApiObject;
import com.work.app.object.NewCoinObject;
import com.work.app.service.CoinService;

@Service
public class CoinServiceImpl implements CoinService {

	@Autowired
	private CoinDao coinDao;

	@Override
	public CoinDeskObject callCoinDeskApi() throws IOException {
		/*
		 * String coindeskApiUri = "https://api.coindesk.com/v1/bpi/currentprice.json";
		 * HttpClient client = HttpClients.createDefault(); HttpGet getcoindeskApi = new
		 * HttpGet(coindeskApiUri); HttpResponse coindeskResponse =
		 * client.execute(getcoindeskApi); String coindeskResponseBody =
		 * EntityUtils.toString(coindeskResponse.getEntity());
		 */
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		String coindeskResponseBody = responseEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();

		CoinDeskObject coinDeskObject = mapper.readValue(coindeskResponseBody, CoinDeskObject.class);
		return coinDeskObject;
	}

	@Override
	public NewCoinApiObject tranformDeskApi() throws IOException {

		CoinDeskObject coinDeskObject = this.callCoinDeskApi();
		NewCoinApiObject newCoinApiObject = new NewCoinApiObject();
		Map<String, NewCoinObject> mapNewCoinObject = new HashMap<String, NewCoinObject>();

		for (String coinName : coinDeskObject.getBpi().keySet()) {
			NewCoinObject newCoinObject = new NewCoinObject();
			String coinTWName = "";
			switch (coinName) {
			case "USD":
				coinTWName = "美金";
				break;
			case "GBP":
				coinTWName = "英鎊";
				break;
			case "EUR":
				coinTWName = "歐元";
				break;
			}
			double exchangeRate = coinDeskObject.getBpi().get(coinName).getRate_float();
			String updateISO = coinDeskObject.getTime().getUpdatedISO();
			DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXXXX");
			OffsetDateTime offsetDateTime = OffsetDateTime.parse(updateISO, DATE_TIME_FORMATTER);
			String updatedTime = offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

			newCoinObject.setCoinName(coinName);
			newCoinObject.setCoinTWName(coinTWName);
			newCoinObject.setExchangeRate(exchangeRate);
			newCoinObject.setUpdateTime(updatedTime);
			mapNewCoinObject.put(coinName, newCoinObject);
		}
		newCoinApiObject.setCoinApi(mapNewCoinObject);
		return newCoinApiObject;
	}

	@Override
	@Transactional
	public void insertCoin(List<CoinObjectJPA> coinItem) throws Exception {

		for (CoinObjectJPA coinObjectJPA : coinItem) {
			coinDao.insertCoin(coinObjectJPA);
		}
	}

	@Override
	@Transactional
	public List<CoinObjectJPA> updateCoin(List<CoinObjectJPA> coinItem) throws Exception {
		List<CoinObjectJPA> listObjectJPA = new ArrayList<CoinObjectJPA>();

		for (CoinObjectJPA coinObjectJPA : coinItem) {
			CoinObjectJPA updateData = coinDao.updateCoin(coinObjectJPA);
			listObjectJPA.add(updateData);
		}
		return listObjectJPA;
	}

	@Override
	public List<CoinObjectJPA> selectCoin(List<String> coinNameItems) throws Exception {
		List<CoinObjectJPA> listObjectJPA = new ArrayList<CoinObjectJPA>();
		listObjectJPA = coinDao.selectCoin(coinNameItems);

		return listObjectJPA;
	}

	@Override
	@Transactional
	public void deleteCoin(List<String> coinNameItems) throws Exception {
		for (String coinName : coinNameItems) {
			coinDao.deleteCoin(coinName);
		}

	}

}
