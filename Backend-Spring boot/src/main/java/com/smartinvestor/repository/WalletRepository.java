package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    public Wallet findByUserId(Long userId);


}
