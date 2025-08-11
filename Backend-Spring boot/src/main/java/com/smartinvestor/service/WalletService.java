package com.smartinvestor.service;


import java.math.BigDecimal;

import com.smartinvestor.exception.WalletException;
import com.smartinvestor.model.Order;
import com.smartinvestor.model.User;
import com.smartinvestor.model.Wallet;

public interface WalletService {


    Wallet getUserWallet(User user) throws WalletException;

    public Wallet addBalanceToWallet(Wallet wallet, Long money) throws WalletException;

    public Wallet findWalletById(Long id) throws WalletException;

    public Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws WalletException;

    public Wallet payOrderPayment(Order order, User user) throws WalletException;



}
