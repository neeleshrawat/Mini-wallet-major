package com.gfg.majorProject.WalletService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.majorProject.TransactionService.models.TransactionCreatedResponse;
import com.gfg.majorProject.UserService.Entities.UserResponse;
import com.gfg.majorProject.WalletService.Entity.Wallet;
import com.gfg.majorProject.WalletService.Entity.WalletResponse;
import com.gfg.majorProject.WalletService.Entity.WalletStatus;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletManagerImpl implements WalletManager{
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    @SneakyThrows
    @Override
    @KafkaListener(topics = "userTopic", groupId = "walletService")
    public void createWallet(String request) {
        UserResponse userResponse = objectMapper.readValue(request,UserResponse.class);
        Wallet wallet = Wallet.builder().balance(1000f).username(userResponse.getUsername()).build();
        walletRepository.save(wallet);


    }


    @SneakyThrows
    @Override
    @KafkaListener(topics = "transactionTopic", groupId = "walletService")
    public void updateWallet(String walletUpdateRequest) {
        TransactionCreatedResponse transactionCreatedResponse
                = objectMapper.readValue(walletUpdateRequest,TransactionCreatedResponse.class);

        Wallet from = walletRepository.findByUsername(transactionCreatedResponse.getFromUser()).get();
        Wallet to = walletRepository.findByUsername(transactionCreatedResponse.getToUser()).get();
        Float amount = transactionCreatedResponse.getAmount();
        if(from.getBalance()-amount>=0){
            from.setBalance(from.getBalance()-amount);
            to.setBalance(to.getBalance()+amount);
            walletRepository.save(from);
            walletRepository.save(to);
            WalletResponse walletResponse = WalletResponse.builder()
                    .success(WalletStatus.SUCCESS).txId(transactionCreatedResponse.getTransactionId()).build();
            kafkaTemplate.send("wallet",objectMapper.writeValueAsString(walletResponse) );

        }else {
            WalletResponse walletResponse = WalletResponse.builder()
                    .success(WalletStatus.FAILURE).txId(transactionCreatedResponse.getTransactionId()).build();
            kafkaTemplate.send("wallet", objectMapper.writeValueAsString(walletResponse));
        }
    }
}
