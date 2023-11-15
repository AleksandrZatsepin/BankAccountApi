package ru.aston.bankaccountapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aston.bankaccountapi.exception.InvalidPinException;
import ru.aston.bankaccountapi.exception.NotEnoughFundsException;
import ru.aston.bankaccountapi.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map <String,String> handleInvalidArgumentException(MethodArgumentNotValidException ex){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errorMap.put(err.getField(), err.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotEnoughFundsException.class)
    public Map<String, String> handleNotEnoughFundsException(NotEnoughFundsException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("custom", ex.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundException(NotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("custom", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidPinException.class)
    public Map<String, String> handleInvalidPinException(InvalidPinException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("custom", ex.getMessage());
        return errorMap;
    }
}
