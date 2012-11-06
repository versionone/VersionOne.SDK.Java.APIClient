package com.versionone.apiclient;

import java.lang.reflect.Method;

class Stringizer {
	Object target;
	String methodName;

	public Stringizer(Object target, String methodName) {
		this.target = target;
		this.methodName = methodName;
	}

	public void dnInvokeDelegate(Object value) {
		try {
			Object[] params = new Object[1];
			params[0] = value;
			 Method[] methods = target.getClass().getDeclaredMethods();
			for (int _i = 0; _i < methods.length; _i++) {
				Method m = methods[_i];
				if (m.getName().equals(methodName)) {
					m.invoke(target, params);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}