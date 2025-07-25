package com.brokingapp.service;

import com.brokingapp.model.Watchlist;
import com.brokingapp.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<Watchlist> getWatchlist(Long userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public Watchlist addToWatchlist(Long userId, String symbol) {
        return watchlistRepository.save(new Watchlist(null, userId, symbol));
    }

    public void removeFromWatchlist(Long userId, String symbol) {
        watchlistRepository.findByUserIdAndSymbol(userId, symbol)
            .ifPresent(watchlistRepository::delete);
    }
} 