package com.gfg.majorProject.TransactionService.manager;

import com.gfg.majorProject.TransactionService.models.TransactionRequest;
import com.gfg.majorProject.TransactionService.models.TransactionResponse;

public interface TransactionManager {

    String create(TransactionRequest transactionRequest, String username);
    TransactionResponse get(String transactionId) throws Exception;
    void updateStatus(String updateRequest) throws Exception;
}
