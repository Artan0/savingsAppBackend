package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.service.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImplementation implements WalletService {
    @Override
    public Wallet newWallet(Long userId) {
        return null;
    }

    @Override
    public Wallet getMyWallet(Long userId) {
        return null;
    }
}
