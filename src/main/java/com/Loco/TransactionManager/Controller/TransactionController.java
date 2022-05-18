package com.Loco.TransactionManager.Controller;

import com.Loco.TransactionManager.Exception.TransactionCreationException;
import com.Loco.TransactionManager.Service.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionController {

    @Autowired
    TransactionService service;


    //Endpoint to get all the transactions of same type

    @RequestMapping("/types/{type}")
    public ArrayList<Long> getType(@PathVariable String type){
        return service.getTransactionByType(type);
    }

    //Endpoint to create a new transaction given id and other details

    @RequestMapping(value="/transaction/{id}", method = RequestMethod.PUT)
    public ResponseEntity addTransaction(@PathVariable("id") String id,@RequestBody String params  ) {

        try {
            JSONObject jsonObject = new JSONObject(params);

            service.addTransactionService(jsonObject,Long.valueOf(id));

        }
       catch (Exception e){
                    throw new TransactionCreationException();
       }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    //Endpoint to get sum of its(transaction) parents(including self)

    @RequestMapping(value="/sum/{id}" , method =RequestMethod.GET)
    public double getTotalSum(@PathVariable("id") String id){
        return service.getTransitiveSum(Long.valueOf(id));

    }

}
