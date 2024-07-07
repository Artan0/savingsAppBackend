package com.example.savingsappbackend.web;

import com.example.savingsappbackend.config.UserAuthenticationProvider;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.models.dto.BalanceHistoryDto;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.service.UserService;
import com.example.savingsappbackend.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WalletController {
    private final WalletService walletService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserService userService;

    public WalletController(WalletService walletService, UserAuthenticationProvider userAuthenticationProvider, UserService userService) {
        this.walletService = walletService;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userService = userService;
    }

    @GetMapping("/wallet/balanceHistory")
    public List<BalanceHistoryDto> getBalanceHistory(@RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);

        return walletService.getBalanceHistory(user.getId()).stream()
                .map(balance -> new BalanceHistoryDto(balance.getDate(), balance.getBudget()))
                .collect(Collectors.toList());
    }
}
