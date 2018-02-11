package com.selenium.listener;

public class PropertiesClass {
	
	Boolean newTest = false;
	String testName;
	String step;
	String methodName;
	String[] parameters;
	String status;
	String failureMessage;
	
	public PropertiesClass(Boolean newTest, String testName, String step, String methodName, String[] parameters, String status, String failureMessage) {
		this.newTest = newTest;
		this.testName = testName;
		this.step = step;
		this.methodName = methodName;
		this.parameters = parameters;
		this.status = status;
		this.failureMessage = failureMessage;
	}

}
