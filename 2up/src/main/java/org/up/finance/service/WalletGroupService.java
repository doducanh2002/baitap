package org.up.finance.service;

import org.up.finance.dto.request.WalletGroupRequest;
import org.up.finance.dto.response.WalletGroupResponse;
import org.up.finance.entity.WalletGroup;

import java.util.List;

public interface WalletGroupService extends BaseService<WalletGroup> {

    /**
     * create a wallet group
     *
     * @param userId - id of current user
     * @param request - from client
     * @return created wallet group
     */
    WalletGroupResponse create(String userId, WalletGroupRequest request);

    /**
     * delete a wallet group
     * @param walletGroupId - from client
     */
    void deleteById(String walletGroupId);

    /**
     * validate a wallet group exist or not
     * @param walletGroupId - id of a wallet group
     */
    void validateExistWalletGroup(String walletGroupId);

    List<WalletGroupResponse> list(String userId);

    WalletGroupResponse get(String userId, String id);

    /**
     * update a wallet group
     * @param userId - id of current user
     * @param walletGroupId - from client
     * @param request - from client
     * @return updated wallet group
     */
    WalletGroupResponse update(String userId, String walletGroupId, WalletGroupRequest request);
}
