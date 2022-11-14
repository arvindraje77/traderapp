package com.acme.mytrader.strategy;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceSource;

@RunWith(MockitoJUnitRunner.class)
public class TradingStrategyTest {

    TradingStrategy tradingStrategy;
    @Mock PriceSource priceSource;
    @Mock ExecutionService broker;
    
    @Before
    public void init() {
        tradingStrategy = new TradingStrategy(broker, 100);
        tradingStrategy.setBuyer("BT", 55.0);
        tradingStrategy.setSeller("BT", 550.0);
    }
    
    @Test
    public void testBuyRationale() {
        tradingStrategy.priceUpdate("BT", 56.0);

        Mockito.verify(broker, Mockito.times(0)).buy(anyString(), anyDouble(), anyInt());
        tradingStrategy.priceUpdate("BT", 54.0);

        Mockito.verify(broker, Mockito.times(1)).buy("IBM", 54.0, 100);
    }
}
