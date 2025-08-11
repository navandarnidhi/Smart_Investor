package com.smartinvestor.service;

import com.smartinvestor.model.Coin;
import com.smartinvestor.model.User;
import com.smartinvestor.model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchList(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin,User user) throws Exception;
}
