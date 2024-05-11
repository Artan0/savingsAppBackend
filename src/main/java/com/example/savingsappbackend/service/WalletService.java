package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.Wallet;

public interface WalletService {
    public Wallet newWallet(Long userId);
    public Wallet getMyWallet(Long userId);
}
