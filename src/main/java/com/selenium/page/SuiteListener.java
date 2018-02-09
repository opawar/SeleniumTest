package com.selenium.page;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener{

	@Override
	public void onFinish(ISuite suite) {		
		
	}

	@Override
	public void onStart(ISuite suite) {
		Properties props = System.getProperties();
		Set<String> set = props.stringPropertyNames();
		Map<String,String> maps = suite.getXmlSuite().getAllParameters();
		for(Map.Entry<String, String> map : maps.entrySet()){
			if(set.contains(map.getKey())){
				map.setValue(props.getProperty(map.getKey()));
			}
		}
		suite.getXmlSuite().setParameters(maps);
	}

}
