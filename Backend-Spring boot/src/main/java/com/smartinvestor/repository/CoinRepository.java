package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.Coin;

public interface CoinRepository extends JpaRepository<Coin,String> {
}
