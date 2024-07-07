package com.example.savingsappbackend.web;

import com.example.savingsappbackend.config.UserAuthenticationProvider;
import com.example.savingsappbackend.models.Transaction;
import com.example.savingsappbackend.models.dto.TransactionDTO;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.service.TransactionService;
import com.example.savingsappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserService userService;
    @Autowired
    public TransactionController(TransactionService transactionService, UserAuthenticationProvider userAuthenticationProvider, UserService userService) {
        this.transactionService = transactionService;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userService = userService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map<String, Object>> getTransactionsByUserId(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize
    ) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Transaction> transactionsPage = transactionService.getAllTransactions(user.getId(), pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("totalItems", transactionsPage.getTotalElements());
        response.put("totalPages", transactionsPage.getTotalPages());
        response.put("currentPage", transactionsPage.getNumber());
        response.put("transactions", transactionsPage.getContent());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestHeader("Authorization") String token
            ,@RequestBody TransactionDTO transactionDTO) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);

        Transaction transaction = transactionService.createTransaction(
                transactionDTO.getTitle(),
                transactionDTO.getDate(),
                transactionDTO.getAmount(),
                transactionDTO.getType(),
                user.getId()
        );
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }
}
