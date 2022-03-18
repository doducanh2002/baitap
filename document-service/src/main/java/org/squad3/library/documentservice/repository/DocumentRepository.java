package org.squad3.library.documentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.squad3.library.documentservice.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
