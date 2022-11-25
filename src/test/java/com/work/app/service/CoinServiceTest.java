package com.work.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.work.app.WorkApplication;
import com.work.app.dao.CoinDao;
import com.work.app.object.CoinDeskObject;
import com.work.app.object.CoinObjectJPA;
import com.work.app.object.NewCoinApiObject;

@SpringBootTest(classes = WorkApplication.class)
@AutoConfigureMockMvc
public class CoinServiceTest {

    @Autowired
    private CoinService coinService;

    @MockBean
    private CoinDao coinDao;

    @Test
    public void callCoinDeskApi() throws Exception {
        CoinDeskObject coinDeskObject = new CoinDeskObject();
        coinDeskObject = coinService.callCoinDeskApi();
        assertEquals("Bitcoin", coinDeskObject.getChartName());
    }

    @Test
    public void tranformDeskApi() throws Exception {
        NewCoinApiObject newCoinApiObject = new NewCoinApiObject();
        newCoinApiObject = coinService.tranformDeskApi();
        assertEquals("USD", newCoinApiObject.getCoinApi().get("USD").getCoinName());
    }

    @Test
    public void insertCoin() throws Exception {
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        List<CoinObjectJPA> coinItem = new ArrayList<CoinObjectJPA>();
        coinItem.add(coinObjectJPA);
        Mockito.doNothing().when(coinDao).insertCoin(coinObjectJPA);
        coinService.insertCoin(coinItem);
    }

    @Test
    public void updateCoin() throws Exception {
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        List<CoinObjectJPA> coinItem = new ArrayList<CoinObjectJPA>();
        coinItem.add(coinObjectJPA);
        Mockito.when(coinDao.updateCoin(coinObjectJPA)).thenReturn(coinObjectJPA);
        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        listCoinObjectJPA = coinService.updateCoin(coinItem);
        assertEquals("USD", listCoinObjectJPA.get(0).getCoinName());
        assertEquals("美金", listCoinObjectJPA.get(0).getCoinTWName());
        assertEquals(123.123, listCoinObjectJPA.get(0).getExchangeRate());
    }
    
    @Test
    public void selectCoin() throws Exception {
        List<String> coinNameItems = new ArrayList<String>();
        coinNameItems.add("USD");
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        List<CoinObjectJPA> coinItem = new ArrayList<CoinObjectJPA>();
        coinItem.add(coinObjectJPA);
        Mockito.when(coinDao.selectCoin(coinNameItems)).thenReturn(coinItem);
        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        listCoinObjectJPA = coinService.selectCoin(coinNameItems);
        assertEquals("USD", listCoinObjectJPA.get(0).getCoinName());
        assertEquals("美金", listCoinObjectJPA.get(0).getCoinTWName());
        assertEquals(123.123, listCoinObjectJPA.get(0).getExchangeRate());
    }
    
    @Test
    public void deleteCoin() throws Exception {
        List<String> coinNameItems = new ArrayList<String>();
        coinNameItems.add("USD");
        Mockito.doNothing().when(coinDao).deleteCoin("USD");
        coinService.deleteCoin(coinNameItems);
    }
}
