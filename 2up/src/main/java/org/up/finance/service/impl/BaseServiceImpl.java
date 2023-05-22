package org.up.finance.service.impl;

import org.up.finance.repository.base.BaseRepository;
import org.up.finance.service.BaseService;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

  private final BaseRepository<T> repository;

  public BaseServiceImpl(BaseRepository<T> repository) {
    this.repository = repository;
  }

  @Override
  public T create(T t) {
    return this.repository.save(t);
  }

  @Override
  public T update(T t) {
    return this.repository.save(t);
  }

  @Override
  public void delete(String id) {
    this.repository.deleteById(id);
  }

  @Override
  public T get(String id) {
    return this.repository.findById(id).orElse(null);
  }

  @Override
  public List<T> list() {
    return this.repository.findAll();
  }
}
