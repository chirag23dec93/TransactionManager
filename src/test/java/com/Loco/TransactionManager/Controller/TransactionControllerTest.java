package com.Loco.TransactionManager.Controller;


import com.Loco.TransactionManager.Service.TransactionService;
import com.Loco.TransactionManager.Transaction.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.Arrays;


@RunWith(SpringRunner.class)

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    TransactionService transactionService;

    Transaction t1 = new Transaction(1, 2000000, "cars", -1);

    Transaction t2 = new Transaction(2, 3500000, "cars", 1);

    Transaction t3 = new Transaction(3, 78000, "shopping", 2);

    Transaction t4 = new Transaction(4, 9900, "shopping", 1);

    Transaction t5 = new Transaction(5, 65400, "shopping", 3);

    Transaction t6 = new Transaction(6, 678.99, "food", 2);

@Test
public void     getFoodType_Success() throws Exception{
ArrayList<Long> testList = new ArrayList<>(Arrays.asList(t6.getTransaction_id()));
   Mockito.when(transactionService.getTransactionByType("food")).thenReturn(testList);

    mockMvc.perform(MockMvcRequestBuilders
            .get("http://localhost:8080/transactionservice/types/food")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0]", is(6)));
}
@Test
public void addTransaction_Success()throws Exception{

    MockHttpServletRequestBuilder builder =
            MockMvcRequestBuilders.put("http://localhost:8080/transactionservice/transaction/13")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(getArticleInJson());
    this.mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status()
                    .isOk())
            .andDo(MockMvcResultHandlers.print());
}
    private String getArticleInJson() {
        return "{\"amount\":\"34.56\", \"type\":\"food\"}";
    }

    @Test
    public void     getSum_Success() throws Exception{
        Mockito.when(transactionService.getTransitiveSum(1)).thenReturn((double)2000000);

        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8080/transactionservice/sum/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(is((double)2000000),Double.class));
    }

}
