package com.user.mngmnt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.user.mngmnt.model.Audit;
import com.user.mngmnt.repository.AuditRepository;

@Service("auditService")
public class AuditServiceImpl implements AuditService {

    @Qualifier("auditRepository")
    @Autowired
    private AuditRepository auditRepository;

    @Override
    public Page<Audit> findAuditByActor(String userName, Pageable pageable) {
        return auditRepository.findByActorIgnoreCase(userName, pageable);
    }

    @Override
    public Page<Audit> findAuditByTarget(String userName, Pageable pageable) {
        return auditRepository.findByTargetIgnoreCase(userName, pageable);
    }

    @Override
    public void saveAudit(Audit audit) {
        auditRepository.save(audit);
        System.out.println("Save audit: "+audit);
    }


    @Override
    public Page<Audit> listAudits(Pageable pageable) {
        return auditRepository.findAll(pageable);
    }


    @Override
    public Page<Audit> searchByTerm(String name, Pageable pageable) {
        return auditRepository.searchByTerm(name, pageable);
    }


    @Override
    public Page<Audit> searchBy(String keyword, String criteria, Pageable pageable) {
        if ("auditor".equals(criteria)) {
            return auditRepository.findByActorIgnoreCase(keyword, pageable);
        } else if ("target".equals(criteria)) {
            return auditRepository.findByTargetIgnoreCase(keyword, pageable);
        } else if ("action".equals(criteria)) {
            return auditRepository.findByActionIgnoreCase(keyword, pageable);
        }
        return null;
    }

}
