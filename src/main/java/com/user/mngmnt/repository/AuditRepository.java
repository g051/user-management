package com.user.mngmnt.repository;

import com.user.mngmnt.model.Audit;
import com.user.mngmnt.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("auditRepository")
public interface AuditRepository extends JpaRepository<Audit, Long> {

    Page<Audit> findByActorIgnoreCase(String userName, Pageable pageable);

    Page<Audit> findByTargetIgnoreCase(String userName, Pageable pageable);

    Page<Audit> findByActionIgnoreCase(String action, Pageable pageable);

    @Query("SELECT t FROM Audit t WHERE " +
            "LOWER(t.actor) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.target) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Audit> searchByTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}
