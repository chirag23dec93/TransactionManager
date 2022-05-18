package com.Loco.TransactionManager.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionController {
    public class ProductExceptionController {
        @ExceptionHandler(value = TransactionCreationException.class)
        public ResponseEntity<Object> exception(TransactionCreationException exception) {
            return new ResponseEntity<>("Transaction can not be created", HttpStatus.NOT_FOUND);
        }
    }
}
