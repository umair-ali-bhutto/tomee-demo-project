package com.ag.service.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ag.entity.Message;
import com.ag.service.MessageService;
import com.ag.util.TomeeLogger;

@Stateless(name = "MessageServiceSender")
public class MessageServiceImpl implements MessageService {

	@PersistenceContext(name = "myPersistenceUnit")
	private EntityManager entityManager;

	@Override
	public void insert(Message mdl) {
		try {
			entityManager.persist(mdl);
		} catch (Exception e) {
			TomeeLogger.logError(getClass(), e);
		}

	}

}
