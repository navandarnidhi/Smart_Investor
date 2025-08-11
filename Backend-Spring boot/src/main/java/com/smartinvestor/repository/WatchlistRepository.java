package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {

    Watchlist findByUserId(Long userId);

}
