package org.up.finance.service;

import org.up.finance.dto.request.WalletRequest;
import org.up.finance.dto.response.WalletResponse;
import org.up.finance.entity.Wallet;

import java.util.List;

public interface WalletService extends BaseService<Wallet> {

    /**
     * create wallet
     * @param userId - id of current user
     * @param walletGroupId - from client
     * @param request - from client
     * @return created wallet
     */
    WalletResponse create(String userId, String walletGroupId, WalletRequest request);

    /**
     * delete a wallet
     * @param userId - id of current user
     * @param walletId - from client
     */
    void delete(String userId, String walletId);

    /**
     * update a wallet
     * @param userId - id of logging user
     * @param walletGroupId - from client
     * @param walletId - from client
     * @param request - from client
     */
    WalletResponse update(String walletId, String walletGroupId, String userId, WalletRequest request);

    /**
     * check exist of wallet
     * @param userId - id of current user
     * @param walletId - from client
     */
    void validateExistWallet(String userId, String walletId);

    /**
     * get wallet by id
     * @param userId - id of current logging user
     * @param walletId - from client
     * @return get wallet by id
     */
    WalletResponse get(String userId, String walletId);

    /**
     * list wallet by user id
     * @param userId - from client
     * @param walletGroupId - from client
     * @return wallet list
     */
    List<WalletResponse> list(String userId, String walletGroupId);
}
