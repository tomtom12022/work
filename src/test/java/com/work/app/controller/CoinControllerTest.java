package com.work.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.work.app.WorkApplication;
import com.work.app.object.CoinDeskObject;
import com.work.app.object.CoinItemObject;
import com.work.app.object.CoinObjectJPA;
import com.work.app.object.NewCoinApiObject;
import com.work.app.object.NewCoinObject;
import com.work.app.object.Request;
import com.work.app.object.Response;
import com.work.app.service.CoinService;

@SpringBootTest(classes = WorkApplication.class)
@AutoConfigureMockMvc
class CoinControllerTest {

    // @Autowired
    // private MockMvc mockMvc;

    @Autowired
    private CoinController coinController;

    @MockBean
    private CoinService coinService;

    @Test
    public void callCoinDeskApi() throws Exception {
        CoinDeskObject coinDeskObject = new CoinDeskObject();
        coinDeskObject.setChartName("test");
        Response<CoinDeskObject> response = new Response<CoinDeskObject>();
        Mockito.when(coinService.callCoinDeskApi()).thenReturn(coinDeskObject);
        response = coinController.callCoinDeskApi();
        assertEquals("0000", response.getResponseCode());
        assertEquals("test", response.getResponseResult().getChartName());
    }
    
    @Test
    public void callCoinDeskApiFail() throws Exception {
        Response<CoinDeskObject> response = new Response<CoinDeskObject>();
        Mockito.when(coinService.callCoinDeskApi()).thenThrow(new IOException());
        response = coinController.callCoinDeskApi();
        assertEquals("9999", response.getResponseCode());
    }
    
    @Test
    public void tranformCoinDeskApi() throws Exception {
        NewCoinApiObject newCoinApiObject = new NewCoinApiObject();
        NewCoinObject newCoinObject = new NewCoinObject();
        newCoinObject.setCoinName("USD");
        Map<String, NewCoinObject> coinApi = new HashMap<String, NewCoinObject>();
        coinApi.put("USD", newCoinObject);
        newCoinApiObject.setCoinApi(coinApi);
        
        Mockito.when(coinService.tranformDeskApi()).thenReturn(newCoinApiObject);
        Response<NewCoinApiObject> response = new Response<NewCoinApiObject>();
        response = coinController.tranformCoinDeskApi();
        assertEquals("0000", response.getResponseCode());
        assertEquals("USD", response.getResponseResult().getCoinApi().get("USD").getCoinName());
    }
    
    @Test
    public void tranformCoinDeskApiFail() throws Exception {
        NewCoinApiObject newCoinApiObject = new NewCoinApiObject();
        NewCoinObject newCoinObject = new NewCoinObject();
        newCoinObject.setCoinName("USD");
        Map<String, NewCoinObject> coinApi = new HashMap<String, NewCoinObject>();
        coinApi.put("USD", newCoinObject);
        newCoinApiObject.setCoinApi(coinApi);
        
        Mockito.when(coinService.tranformDeskApi()).thenThrow(new IOException());
        Response<NewCoinApiObject> response = new Response<NewCoinApiObject>();
        response = coinController.tranformCoinDeskApi();
        assertEquals("9999", response.getResponseCode());
    }
    
    @Test
    public void insertCoin() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinObjectItem = new CoinItemObject();
        coinObjectItem.setCoinItem(null);
        request.setRequestItem(coinObjectItem);
        Mockito.doNothing().when(coinService).insertCoin(request.getRequestItem().getCoinItem());
        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.insertCoin(request);
        assertEquals("0000", response.getResponseCode());
    }

    @Test
    public void insertCoinFail() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinObjectItem = new CoinItemObject();
        coinObjectItem.setCoinItem(null);
        request.setRequestItem(coinObjectItem);
        Mockito.doThrow(new Exception()).when(coinService).insertCoin(request.getRequestItem().getCoinItem());
        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.insertCoin(request);
        assertEquals("9999", response.getResponseCode());
    }

    @Test
    public void updateCoin() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinItemObject = new CoinItemObject();
        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        coinObjectJPA.setUpdateTime("2022/11/25 18:00:00");
        listCoinObjectJPA.add(coinObjectJPA);
        coinItemObject.setCoinItem(listCoinObjectJPA);
        request.setRequestItem(coinItemObject);
        Mockito.when(coinService.updateCoin(request.getRequestItem().getCoinItem())).thenReturn(listCoinObjectJPA);

        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.updateCoin(request);

        assertEquals("0000", response.getResponseCode());
        assertEquals("USD", response.getResponseResult().getCoinItem().get(0).getCoinName());
        assertEquals("美金", response.getResponseResult().getCoinItem().get(0).getCoinTWName());
        assertEquals(123.123, response.getResponseResult().getCoinItem().get(0).getExchangeRate());
        assertEquals("2022/11/25 18:00:00", response.getResponseResult().getCoinItem().get(0).getUpdateTime());
    }

    @Test
    public void updateCoinFail() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinItemObject = new CoinItemObject();
        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USDDDDDDD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        coinObjectJPA.setUpdateTime("2022/11/25 18:00:00");
        listCoinObjectJPA.add(coinObjectJPA);
        coinItemObject.setCoinItem(listCoinObjectJPA);
        request.setRequestItem(coinItemObject);
        Mockito.when(coinService.updateCoin(request.getRequestItem().getCoinItem())).thenThrow(new Exception());
        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.updateCoin(request);
        assertEquals("9999", response.getResponseCode());
    }

    @Test
    public void selectCoin() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinItemObject = new CoinItemObject();
        List<String> listCoinNameItems = new ArrayList<String>();
        listCoinNameItems.add("USD");
        coinItemObject.setCoinNameItems(listCoinNameItems);
        request.setRequestItem(coinItemObject);

        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();

        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        coinObjectJPA.setUpdateTime("2022/11/25 18:00:00");
        listCoinObjectJPA.add(coinObjectJPA);
        coinItemObject.setCoinItem(listCoinObjectJPA);
        Mockito.when(coinService.selectCoin(request.getRequestItem().getCoinNameItems())).thenReturn(listCoinObjectJPA);

        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.selectCoin(request);
        assertEquals("0000", response.getResponseCode());
        assertEquals("USD", response.getResponseResult().getCoinItem().get(0).getCoinName());
        assertEquals("美金", response.getResponseResult().getCoinItem().get(0).getCoinTWName());
        assertEquals(123.123, response.getResponseResult().getCoinItem().get(0).getExchangeRate());
        assertEquals("2022/11/25 18:00:00", response.getResponseResult().getCoinItem().get(0).getUpdateTime());
    }

    @Test
    public void selectCoinFail() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinItemObject = new CoinItemObject();
        List<String> listCoinNameItems = new ArrayList<String>();
        listCoinNameItems.add("USD");
        coinItemObject.setCoinNameItems(listCoinNameItems);
        request.setRequestItem(coinItemObject);

        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();

        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        coinObjectJPA.setUpdateTime("2022/11/25 18:00:00");
        listCoinObjectJPA.add(coinObjectJPA);
        coinItemObject.setCoinItem(listCoinObjectJPA);
        Response<CoinItemObject> response = new Response<CoinItemObject>();

        Mockito.when(coinService.selectCoin(request.getRequestItem().getCoinNameItems())).thenThrow(new Exception());
        response = coinController.selectCoin(request);
        assertEquals("9999", response.getResponseCode());
    }

    @Test
    public void deleteCoin() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinItemObject = new CoinItemObject();
        List<String> listCoinNameItems = new ArrayList<String>();
        listCoinNameItems.add("USD");
        coinItemObject.setCoinNameItems(listCoinNameItems);
        request.setRequestItem(coinItemObject);
        Mockito.doNothing().when(coinService).deleteCoin(request.getRequestItem().getCoinNameItems());
        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.deleteCoin(request);
        assertEquals("0000", response.getResponseCode());
    }

    @Test
    public void deleteCoinFail() throws Exception {
        Request<CoinItemObject> request = new Request<CoinItemObject>();
        CoinItemObject coinItemObject = new CoinItemObject();
        List<String> listCoinNameItems = new ArrayList<String>();
        listCoinNameItems.add("USD");
        coinItemObject.setCoinNameItems(listCoinNameItems);
        request.setRequestItem(coinItemObject);
        Mockito.doThrow(new Exception()).when(coinService).deleteCoin(request.getRequestItem().getCoinNameItems());
        Response<CoinItemObject> response = new Response<CoinItemObject>();
        response = coinController.deleteCoin(request);
        assertEquals("9999", response.getResponseCode());
    }
}
