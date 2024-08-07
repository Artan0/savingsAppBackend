package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.models.dto.BalanceHistoryDto;
import com.example.savingsappbackend.repository.WalletRepository;
import com.example.savingsappbackend.service.WalletService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletServiceImplementation implements WalletService {
    private final WalletRepository walletRepository;
    public WalletServiceImplementation(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet newWallet(Long userId) {
        return null;
    }

    @Override
    public Wallet findByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }


    @Override
    public List<BalanceHistoryDto> getBalanceHistory(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        return wallet.getHistoryMap().entrySet().stream()
                .filter(entry -> (entry.getKey().isAfter(sevenDaysAgo) || entry.getKey().isEqual(sevenDaysAgo)) && entry.getKey().isBefore(today.plusDays(1)))
                .map(entry -> new BalanceHistoryDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void updateDailyHistory() {
        Iterable<Wallet> wallets = walletRepository.findAll();
        LocalDate today = LocalDate.now();

        wallets.forEach(wallet -> {
            Double currentBudget = wallet.getBudget();
            wallet.addHistory(today, currentBudget);
            walletRepository.save(wallet);
            System.out.println("Updated wallet " + wallet.getId() + " with budget " + currentBudget + " for date " + today);
        });
    }

}
