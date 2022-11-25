package com.work.app.dao;

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
import com.work.app.object.CoinObjectJPA;

@SpringBootTest(classes = WorkApplication.class)
@AutoConfigureMockMvc
public class CoinDaoTest {

    @Autowired
    private CoinDao coinDao;

    @MockBean
    private CoinRepository coinRespository;

    @Test
    public void insertCoin() throws Exception {
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        Mockito.when(coinRespository.save(coinObjectJPA)).thenReturn(coinObjectJPA);
        coinDao.insertCoin(coinObjectJPA);
    }

    @Test
    public void updateCoin() throws Exception {
        CoinObjectJPA coinObjectJPA = new CoinObjectJPA();
        coinObjectJPA.setCoinName("USD");
        coinObjectJPA.setCoinTWName("美金");
        coinObjectJPA.setExchangeRate(123.123);
        CoinObjectJPA updateData = new CoinObjectJPA();
        updateData.setCoinName(coinObjectJPA.getCoinName());
        updateData.setCoinName(coinObjectJPA.getCoinTWName());
        updateData.setCoinName(coinObjectJPA.getCoinName());

        Mockito.when(coinRespository.findByCoinName(coinObjectJPA.getCoinName())).thenReturn(updateData);
        Mockito.when(coinRespository.save(updateData)).thenReturn(updateData);
        updateData = new CoinObjectJPA();
        updateData = coinDao.updateCoin(coinObjectJPA);
        assertEquals("USD", updateData.getCoinName());
        assertEquals("美金", updateData.getCoinTWName());
        assertEquals(123.123, updateData.getExchangeRate());

    }

    @Test
    public void selectCoinByCoinName() throws Exception {
        List<String> coinNameItems = new ArrayList<String>();
        coinNameItems.add("USD");
        CoinObjectJPA updateData = new CoinObjectJPA();
        updateData.setCoinName("USD");
        updateData.setCoinTWName("美金");
        updateData.setExchangeRate(123.123);
        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        listCoinObjectJPA.add(updateData);
        Mockito.when(coinRespository.findByCoinNameIn(coinNameItems)).thenReturn(listCoinObjectJPA);
        List<CoinObjectJPA> returnlistCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        returnlistCoinObjectJPA = coinDao.selectCoin(coinNameItems);
        assertEquals("USD", returnlistCoinObjectJPA.get(0).getCoinName());
        assertEquals("美金", returnlistCoinObjectJPA.get(0).getCoinTWName());
        assertEquals(123.123, returnlistCoinObjectJPA.get(0).getExchangeRate());
    }
    
    @Test
    public void selectCoinFindAll() throws Exception {
        List<String> coinNameItems = null;
        List<CoinObjectJPA> listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        listCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        
        CoinObjectJPA updateData = new CoinObjectJPA();
        updateData.setCoinName("USD");
        updateData.setCoinTWName("美金");
        updateData.setExchangeRate(123.123);
        listCoinObjectJPA.add(updateData);
        
        CoinObjectJPA updateData2 = new CoinObjectJPA();
        updateData2.setCoinName("EUR");
        updateData2.setCoinTWName("歐元");
        updateData2.setExchangeRate(150.150);
        listCoinObjectJPA.add(updateData2);
        
        Mockito.when(coinRespository.findAll()).thenReturn(listCoinObjectJPA);
        List<CoinObjectJPA> returnlistCoinObjectJPA = new ArrayList<CoinObjectJPA>();
        returnlistCoinObjectJPA = coinDao.selectCoin(coinNameItems);
        
        assertEquals("USD", returnlistCoinObjectJPA.get(0).getCoinName());
        assertEquals("美金", returnlistCoinObjectJPA.get(0).getCoinTWName());
        assertEquals(123.123, returnlistCoinObjectJPA.get(0).getExchangeRate());
        assertEquals("EUR", returnlistCoinObjectJPA.get(1).getCoinName());
        assertEquals("歐元", returnlistCoinObjectJPA.get(1).getCoinTWName());
        assertEquals(150.150, returnlistCoinObjectJPA.get(1).getExchangeRate());
    }
    @Test
    public void deleteCoin() throws Exception {
        Mockito.doNothing().when(coinRespository).deleteByCoinName("USD");
        coinDao.deleteCoin("USD");
    }

}
