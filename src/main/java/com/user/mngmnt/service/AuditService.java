package com.user.mngmnt.service;

import com.user.mngmnt.model.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuditService {

    Page<Audit> findAuditByActor(String userName, Pageable pageable);

    Page<Audit> findAuditByTarget(String userName, Pageable pageable);

    void saveAudit(Audit audit);

    Page<Audit> searchByTerm(String name, Pageable pageable);

    Page<Audit> listAudits(Pageable pageable);

    Page<Audit> searchBy(String keyword, String criteria, Pageable pageable);
}
