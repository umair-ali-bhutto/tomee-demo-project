package com.ag.service.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ag.entity.AuditLog;
import com.ag.service.AuditLogService;
import com.ag.util.TomeeLogger;

@Stateless
public class AuditLogServiceImpl implements AuditLogService {

	@PersistenceContext(name = "myPersistenceUnit")
	private EntityManager entityManager;

	@Override
	public void insertLog(AuditLog log) {
		try {
			entityManager.persist(log);
		} catch (Exception e) {
			TomeeLogger.logError(getClass(), e);
		}
	}

}
