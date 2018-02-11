package com.selenium.listener;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

public class PdfListener extends SuiteListener implements ITestListener {
	
	File imageFile;
	Integer counter;
	String step;
	String methodName;
	String[] parameters;
	String status;
	String failureMessage;
	String testName;
	Boolean newTest = false;
	
	@Override
	public void onFinish(ITestContext context) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		counter = 1;
		
		XmlTest test = context.getCurrentXmlTest();
		testName = test.getName();
		
		step = "";
		methodName = "";
		parameters = new String[0];
		status = "";
		failureMessage = "";
		
		properties.add(new PropertiesClass(true, testName, step, methodName, parameters, status, failureMessage));
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		step = counter.toString();
		methodName = result.getName();
		parameters = Arrays.copyOf(result.getParameters(), result.getParameters().length, String[].class);
		status = "Failed";
		failureMessage = result.getThrowable().getLocalizedMessage();
		
		properties.add(new PropertiesClass(false, testName, step, methodName, parameters, status, failureMessage));
		
		takeScreenshot(methodName);
		
		counter++;
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		step = counter.toString();
		methodName = result.getName();
		parameters = Arrays.copyOf(result.getParameters(), result.getParameters().length, String[].class);
		status = "Skipped";
		failureMessage = "";
		
		properties.add(new PropertiesClass(false, testName, step, methodName, parameters, status, failureMessage));
		
		counter++;
	}

	@Override
	public void onTestStart(ITestResult result) {
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		step = counter.toString();
		methodName = result.getName();
		parameters = Arrays.copyOf(result.getParameters(), result.getParameters().length, String[].class);
		status = "Passed";
		failureMessage = "";
		
		properties.add(new PropertiesClass(false, testName, step, methodName, parameters, status, failureMessage));
		
		takeScreenshot(methodName);
		
		counter++;
	}
	
	public void takeScreenshot(String methodName) {
		try {
			Robot robot = new Robot();
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenSize = new Rectangle(dimension);
			BufferedImage image = robot.createScreenCapture(screenSize);
			imageFile = new File(System.getProperty("user.dir") + ("\\target\\generated-images\\" + methodName + ".jpg"));
			ImageIO.write(image, "jpg", imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
