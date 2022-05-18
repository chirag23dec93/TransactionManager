package com.Loco.TransactionManager.Transaction;


import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class Transaction {

    private long transaction_id;
    private double amount;
    private String type;
    private long parent_id;


    public  Transaction(){

    }
    public  Transaction(long transaction_id , double amount, String type,long parent_id){
        this.amount =amount;
        this.type =type;
        this.parent_id =parent_id;
        this.transaction_id=transaction_id;

    }


    public void setTransaction_id(long transaction_id){
        this.transaction_id =transaction_id;
    }

    public long getTransaction_id(){
        return transaction_id;
    }

    public  void setParent_id(long parent_id){
        this.parent_id= parent_id;
    }

    public long getParent_id(){
        return this.parent_id;
    }
    public void setAmount(double amount){
        this.amount =amount;

    }
    public double getAmount(){
        return this.amount;
    }

    public void setType(String type){
        this.type =type;
    }

    public String getType(){
        return this.type;
    }
}
