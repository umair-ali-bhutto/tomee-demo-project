package com.ag.util;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

public class TomeeLogger {

	private static final Logger logger = Logger.getLogger(TomeeLogger.class);

    public static void logInfo(String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public static void logError(Class<?> obj, Exception e) {
        final Logger logger = Logger.getLogger(obj);
        logger.error(e.getMessage(), e);
        if (!(e instanceof NoResultException)) {
            e.printStackTrace();
        }
    }

}
