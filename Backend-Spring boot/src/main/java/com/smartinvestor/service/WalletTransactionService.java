package com.smartinvestor.service;

import java.util.List;

import com.smartinvestor.domain.WalletTransactionType;
import com.smartinvestor.model.Wallet;
import com.smartinvestor.model.WalletTransaction;

public interface WalletTransactionService {
    WalletTransaction createTransaction(Wallet wallet,
                                        WalletTransactionType type,
                                        String transferId,
                                        String purpose,
                                        Long amount
    );

    List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);

}
