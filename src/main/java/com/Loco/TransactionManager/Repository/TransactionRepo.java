package com.Loco.TransactionManager.Repository;


import com.Loco.TransactionManager.Exception.TransactionCreationException;
import com.Loco.TransactionManager.Transaction.Transaction;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;


@Component
public class TransactionRepo {


    // for mapping transactions on the basis of their types
    HashMap<String, ArrayList<Transaction>> map = new HashMap<>();

    ArrayList<Transaction> transactions = new ArrayList<>();

    HashMap<Long, Long> parent = new HashMap<>();

    HashMap<Long, Transaction> transactionsListId = new HashMap<>();

    @PostConstruct
    public void init() {
        createTransactions();
        storeTransactionByType();
        setParent();
        storeTransactionsById();
    }

    //This method creates transactions in memory , just for the start

    public ArrayList<Transaction> createTransactions() {

        Transaction t1 = new Transaction(1, 2000000, "cars", -1);
        transactions.add(t1);
        Transaction t2 = new Transaction(2, 3500000, "cars", 1);
         transactions.add(t2);
        Transaction t3 = new Transaction(3, 78000, "shopping", 2);
        transactions.add(t3);
        Transaction t4 = new Transaction(4, 9900, "shopping", 1);
        transactions.add(t4);
        Transaction t5 = new Transaction(5, 65400, "shopping", 3);
        transactions.add(t5);
        Transaction t6 = new Transaction(6, 678.99, "food", 2);
        transactions.add(t6);

        return transactions;
    }

    // This method sets parent matrix for all the transactions

    private void setParent(){
        for(int i =0 ;i<transactions.size();i++){
            parent.put(transactions.get(i).getTransaction_id(),transactions.get(i).getParent_id());
        }
    }

    // This method stores all the transactions by their id as Key

    private void storeTransactionsById(){
        for(int i = 0;i< transactions.size();i++){
            transactionsListId.put(transactions.get(i).getTransaction_id(),transactions.get(i));
        }


    }

    //This method creates a Hashmap for all the related transactions by their type

    private void storeTransactionByType() {
        for (int i = 0; i < transactions.size(); i++) {
            if (map.containsKey(transactions.get(i).getType())) {
                Transaction t = transactions.get(i);
                ArrayList<Transaction> a = map.get(t.getType());
                a.add(t);
                map.put(t.getType(), a);
            } else {
                ArrayList<Transaction> a = new ArrayList<>();
                a.add(transactions.get(i));
                map.put(transactions.get(i).getType(), a);
            }
        }
    }

    // This method retrieves transactions by type

    public ArrayList<Long> getTransactionByType(String type) {

        ArrayList<Long> listOfIds = new ArrayList<>();
        for (String types : map.keySet()) {
            if (types.compareTo(type) == 0) {
                ArrayList<Transaction> transactionsType = map.get(types);
                for(Transaction t :transactionsType){
                    listOfIds.add(t.getTransaction_id());
                }
                return listOfIds;
            }
        }
        return null;

    }

    //This method creates new transaction

    public boolean createNewTransaction(JSONObject jsonTransaction,Long transaction_id) {
        Transaction newTransaction = new Transaction();
        try {
            if (!jsonTransaction.getString("amount").isEmpty())
                newTransaction.setAmount(Double.valueOf(jsonTransaction.getString("amount")));
            else
                newTransaction.setAmount(0.0d);
            if (!jsonTransaction.getString("type").isEmpty())
                newTransaction.setType(jsonTransaction.getString("type"));
            else
                newTransaction.setType(null);
            if (!jsonTransaction.getString("parent_id").isEmpty())
                newTransaction.setParent_id(Long.valueOf(jsonTransaction.getInt("parent_id")));
            else
                newTransaction.setParent_id(-1);

            newTransaction.setTransaction_id(transaction_id);

            insertByTypeRequest(newTransaction);

            // here updating the parent matrix for newly added transaction

            parent.put(newTransaction.getTransaction_id(),newTransaction.getParent_id());

            // here updating transaction id hashmap for newly added transaction

            transactionsListId.put(newTransaction.getTransaction_id(),newTransaction);
            return true;
        } catch (Exception e) {
                throw new TransactionCreationException();
        }

    }

    //  This method is used to update Type hashmap , as new transaction has entered

    public void insertByTypeRequest(Transaction t) {
        if (map.containsKey(t.getType())) {
            ArrayList<Transaction> a = map.get(t.getType());
            a.add(t);
            map.put(t.getType(),a);
        }
        else{
            ArrayList<Transaction>  a = new ArrayList<>();
            map.put(t.getType(),a);
        }
    }

    //This method is used to calculate Transitive sum from hashmap storing parents
    //For eg , if input is 3  , then first  amount related to transaction_id =3 is added to sum
    // then find parent of 3 , if parent  !=-1 , the continue to add sum and change parent id
    // if parent =-1 then stop and return the sum

    public double  getSum(long transaction_id){
        double sum =0.0;
        long copyTransactionId =transaction_id;
        sum+=transactionsListId.get(transaction_id).getAmount();
        if(parent.containsKey(transaction_id)){
            while(true){
                long newParent = parent.get(copyTransactionId);
                if(newParent!=-1 )
                    sum+=transactionsListId.get(newParent).getAmount();
                else
                    break;
                copyTransactionId = newParent;
            }
        }
        return sum;

    }

}


