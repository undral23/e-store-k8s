package edu.miu.sa.transaction.card.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transaction/card")
@Slf4j
public class CardTransactionController {

    @PostMapping
    public ResponseEntity<?> doTransaction(@RequestBody Map<String, Object> txnData, Authentication auth) {
        String userId = (String) auth.getPrincipal();
        log.info("Inside doTransaction of CardTransactionController");
        log.info("userId: " + userId);

        txnData.put("transactionNumber", System.currentTimeMillis());

        log.info("====== Txn Data =======");
        for (String key : txnData.keySet()) {
            log.info(key + " : " + txnData.get(key));
        }
        log.info("=======================");

        return ResponseEntity.ok(txnData);
    }
}
