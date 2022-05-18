package com.Loco.TransactionManager.Service;

import org.json.JSONObject;

import java.util.ArrayList;

public interface TransactionService {

    public ArrayList<Long> getTransactionByType(String type);

    public boolean addTransactionService(JSONObject jsonTransaction, Long transaction_id);

    public double getTransitiveSum(long id);
}
