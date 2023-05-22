package org.up.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.up.finance.dto.request.WalletGroupRequest;
import org.up.finance.dto.response.WalletGroupResponse;
import org.up.finance.entity.WalletGroup;
import org.up.finance.exception.WalletGroupParentConstraintException;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.WalletGroupRepository;
import org.up.finance.service.WalletGroupService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class WalletGroupServiceImpl extends BaseServiceImpl<WalletGroup> implements WalletGroupService {

    private final WalletGroupRepository repository;

    public WalletGroupServiceImpl(WalletGroupRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional
    public WalletGroupResponse create(String userId, WalletGroupRequest request) {
        validateParentId(request.getParentId());
        WalletGroup walletGroup = WalletGroup.from(userId, request);
        walletGroup = create(walletGroup);
        return WalletGroupResponse.from(walletGroup);
    }

    @Override
    public List<WalletGroupResponse> list(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(WalletGroupResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public WalletGroupResponse get(String userId, String id) {
        var walletGroup = repository.find(userId, id).orElseThrow(() -> {
            log.error("(get)id : {} --> NOT FOUND EXCEPTION", id);
            throw new NotFoundException(id, WalletGroup.class.getSimpleName());
        });
        return WalletGroupResponse.from(walletGroup);
    }

    @Override
    public void deleteById(String walletGroupId) {
        validateExistWalletGroup(walletGroupId);
        if (repository.existSubWalletGroupById(walletGroupId)) {
            throw new WalletGroupParentConstraintException(walletGroupId);
        }
        delete(walletGroupId);
    }

    @Override
    public void validateExistWalletGroup(String walletGroupId) {
        log.info("(validateExistWalletGroup)walletGroupId : {}", walletGroupId);
        if (!repository.existsById(walletGroupId)) {
            log.error("(validateExistWalletGroup)walletGroupId : {}", walletGroupId);
            throw new NotFoundException(walletGroupId, WalletGroup.class.getSimpleName());
        }
    }

    @Override
    @Transactional
    public WalletGroupResponse update(String userId, String walletGroupId, WalletGroupRequest request) {
        WalletGroup walletGroup = repository.find(userId, walletGroupId).map((wg) -> {
            validateParentId(request.getParentId());
            wg.setName(request.getName());
            wg.setParentId(request.getParentId());
            wg = update(wg);
            return wg;
        }).orElseThrow(() -> {
            log.error("(update)walletGroupId : {} --> NOT FOUND EXCEPTION", walletGroupId);
            throw new NotFoundException(walletGroupId, WalletGroup.class.getSimpleName());
        });
        return WalletGroupResponse.from(walletGroup);
    }

    /**
     * validate exist of a parent id
     *
     * @param parentId - request from client
     */
    private void validateParentId(String parentId) {
        log.info("(validateParentId)walletGroupId : {}", parentId);
        if (Objects.nonNull(parentId)) {
            validateExistWalletGroup(parentId);
        }
    }


}
