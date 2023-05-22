package org.up.finance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.up.finance.entity.Category;
import org.up.finance.repository.base.BaseRepository;

import java.util.List;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    List<Category> findByUserId(String userId);
    @Query(value = "select case when exists(" +
            "select 1 from category c where c.category_parent_id = :id)" +
            "then 'true' else 'false' end", nativeQuery = true)
    boolean existSubCategoryById(String id);

    Optional<Category> findByIdAndUserId(String categoryId,String userId);
}
