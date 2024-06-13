package com.ag.test;

import com.ag.util.TomeeLogger;

public class Test {

	public static void main(String[] args) {
		try {
			TomeeLogger.logInfo("TEST");
			throw new NullPointerException();
		
		} catch (Exception e) {
			TomeeLogger.logError(Test.class, e);
		}
	}
}
