package com.nacho.sportradar.bettingprocessservice.Controller;


import com.nacho.sportradar.bettingprocessservice.Controller.Response.CustomErrorResponse;
import com.nacho.sportradar.bettingprocessservice.Exception.GettingTopResultsClientsException;
import com.nacho.sportradar.bettingprocessservice.Exception.GettingTotalBetAmountException;
import com.nacho.sportradar.bettingprocessservice.Exception.GettingTotalBetResultsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    /* CODES */
    private static final String ERROR_GETTING_TOTAL_AMOUNT = "ERROR_GETTING_TOTAL_AMOUNT";
    private static final String ERROR_GETTING_TOTAL_RESULTS = "ERROR_GETTING_TOTAL_RESULTS";
    private static final String ERROR_GETTING_TOP_RESULTS_CLIENTS = "ERROR_GETTING_TOP_PROFIT_OR_LOSSES_CLIENTS";

    @ExceptionHandler(GettingTotalBetAmountException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleGettingAmountException(GettingTotalBetAmountException e) {
        return new CustomErrorResponse(ERROR_GETTING_TOTAL_AMOUNT);
    }

    @ExceptionHandler(GettingTotalBetResultsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleGettingResultsException(GettingTotalBetResultsException e) {
        return new CustomErrorResponse(ERROR_GETTING_TOTAL_RESULTS);
    }

    @ExceptionHandler(GettingTopResultsClientsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleGettingTopClientsException(GettingTopResultsClientsException e) {
        return new CustomErrorResponse(ERROR_GETTING_TOP_RESULTS_CLIENTS);
    }


}
