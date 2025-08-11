package com.smartinvestor.service;



import java.util.List;

import com.smartinvestor.model.Asset;
import com.smartinvestor.model.Coin;
import com.smartinvestor.model.User;

public interface AssetService {
    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetId);

    Asset getAssetByUserAndId(Long userId,Long assetId);

    List<Asset> getUsersAssets(Long userId);

    Asset updateAsset(Long assetId,double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId,String coinId) throws Exception;

    void deleteAsset(Long assetId);


}
