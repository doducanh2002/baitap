package org.up.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.up.finance.dto.request.WalletRequest;
import org.up.finance.dto.response.WalletResponse;
import org.up.finance.entity.Wallet;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.WalletRepository;
import org.up.finance.service.WalletService;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class WalletServiceImpl extends BaseServiceImpl<Wallet> implements WalletService {

  private final WalletRepository repository;

  public WalletServiceImpl(WalletRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional
  public WalletResponse create(String userId, String walletGroupId, WalletRequest request) {
    log.info("(create)request : {}", request);
    Wallet wallet = Wallet.of(userId, walletGroupId, request);
    wallet = create(wallet);
    return WalletResponse.from(wallet);
  }

  @Override
  @Transactional
  public void delete(String userId, String walletId) {
    log.info("(delete)userId : {}, walletId : {}", userId, walletId);
    validateExistWallet(userId, walletId);
    delete(walletId);
  }

  @Override
  public void validateExistWallet(String userId, String walletId) {
    log.info("(validateExistWallet)userId : {}, walletId : {}", userId, walletId);
    if (!repository.existsByIdAndUserId(walletId, userId)) {
      throw new NotFoundException(walletId, Wallet.class.getSimpleName());
    }
  }

  @Override
  public WalletResponse get(String userId, String walletId) {
    log.info("(get)userId : {}, walletId : {}", userId, walletId);
    Wallet wallet =
        repository
            .findByUserIdAndId(userId, walletId)
            .orElseThrow(
                () -> {
                  log.error("(get)walletId : {} --> NOT FOUND EXCEPTION", walletId);
                  throw new NotFoundException(walletId, Wallet.class.getSimpleName());
                });
    return WalletResponse.from(wallet);
  }

  @Override
  public List<WalletResponse> list(String userId, String walletGroupId) {
    log.info("(list)userId : {}", userId);
    return repository.findByUserIdAndWalletGroupId(userId, walletGroupId).stream()
            .map(WalletResponse::from)
            .collect(Collectors.toList());
  }

  @Override
  public WalletResponse update(
          String walletId, String walletGroupId, String userId, WalletRequest request) {
    log.info(
            "(update)userId : {}, walletGroupId : {}, walletId : {}, request : {}",
            userId,
            walletGroupId,
            walletId,
            request);
    Wallet wallet =
            repository
                    .findByIdAndWalletGroupIdAndUserId(walletId, walletGroupId, userId)
                    .orElseThrow(
                            () -> {
                              log.error(
                                      "(update)walletId : {}, userId : {} --> NOT FOUND EXCEPTION",
                                      walletId,
                                      userId);
                              throw new NotFoundException(walletId, Wallet.class.getSimpleName());
                            });

    wallet.setName(request.getName());
    wallet.setAmount(request.getAmount() != null ? request.getAmount() : wallet.getAmount());
    wallet.setDescription(request.getDescription());
    wallet = repository.save(wallet);
    return WalletResponse.from(wallet);
  }

}
