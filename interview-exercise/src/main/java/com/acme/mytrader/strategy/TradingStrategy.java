package com.acme.mytrader.strategy;

import java.util.HashMap;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy implements PriceListener{

    private ExecutionService service;
    private HashMap<String, Double> buyer;
    private HashMap<String, Double> seller;
    private static Integer defaultLot = 10;
    private int lotSize;
    
    public TradingStrategy(ExecutionService service, Integer lot) {
        this.service = service;
        this.buyer = new HashMap<String,Double>();
        this.seller = new HashMap<String,Double>();
        this.lotSize = lot;
    }

    public TradingStrategy(ExecutionService service) {
        this(service, defaultLot);
    }
    
	@Override
	public void priceUpdate(String security, double price) {
		applyStrategy(security, price);
		
	}
	
	private void applyStrategy(String security, Double price){
        Double buyThreshold = buyer.get(security);
        Double sellThreshold = seller.get(security);
        if(buyThreshold != null && price < buyThreshold){
        	service.buy(security, price, getLotSize());
        }
        if (sellThreshold != null && price > sellThreshold) {
        	service.sell(security, price, getLotSize());
        }
    }
	
	public void setBuyer(String security, Double buyThreshold){
	     buyer.put(security, buyThreshold);
	}
	 
	public void setSeller(String security, Double sellThreshold){
	    seller.put(security, sellThreshold);
	}
	
 	public int getLotSize() {
		return lotSize;
	}

	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}
	
}
