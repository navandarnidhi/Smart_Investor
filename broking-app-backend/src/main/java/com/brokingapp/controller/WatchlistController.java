package com.brokingapp.controller;

import com.brokingapp.model.Watchlist;
import com.brokingapp.service.WatchlistService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    @GetMapping("/{userId}")
    public List<Watchlist> getWatchlist(@PathVariable Long userId) {
        return watchlistService.getWatchlist(userId);
    }

    @PostMapping("/add")
    public Watchlist addToWatchlist(@RequestBody WatchlistRequest request) {
        return watchlistService.addToWatchlist(request.getUserId(), request.getSymbol());
    }

    @DeleteMapping("/remove")
    public void removeFromWatchlist(@RequestBody WatchlistRequest request) {
        watchlistService.removeFromWatchlist(request.getUserId(), request.getSymbol());
    }

    @Data
    public static class WatchlistRequest {
        private Long userId;
        private String symbol;
    }
} 