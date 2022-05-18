package com.Loco.TransactionManager.Service;

import com.Loco.TransactionManager.Exception.TransactionCreationException;
import com.Loco.TransactionManager.Repository.TransactionRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepo repository;


    public ArrayList<Long> getTransactionByType(String type){
        return repository.getTransactionByType(type);
    }

    public boolean addTransactionService(JSONObject jsonTransaction,Long transaction_id){
        if(jsonTransaction.length()==0){
            return true;
        }
        try {
            return repository.createNewTransaction(jsonTransaction, transaction_id);
        }
        catch (Exception e){
            throw new TransactionCreationException();
        }

    }

    public double getTransitiveSum(long id){
        return repository.getSum(id);

    }


}
