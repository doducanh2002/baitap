package org.up.finance.service;

import java.util.List;

public interface BaseService<T> {
  T create(T t);

  T update(T t);

  void delete(String id);

  T get(String id);

  List<T> list();
}
