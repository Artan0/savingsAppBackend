package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.models.dto.BalanceHistoryDto;

import java.util.List;

public interface WalletService {
    Wallet newWallet(Long userId);
    Wallet findByUserId(Long userId);
    List<BalanceHistoryDto> getBalanceHistory(Long userId);


}
