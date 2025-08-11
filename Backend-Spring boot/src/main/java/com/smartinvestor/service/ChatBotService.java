package com.smartinvestor.service;

import com.smartinvestor.model.CoinDTO;
import com.smartinvestor.response.ApiResponse;

public interface ChatBotService {
    ApiResponse getCoinDetails(String coinName);

    CoinDTO getCoinByName(String coinName);

    String simpleChat(String prompt);
}
