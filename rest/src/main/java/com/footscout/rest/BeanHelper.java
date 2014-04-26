package com.footscout.rest;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BeanHelper {
	private List<Method> setters;
	
	public BeanHelper(Class<?> beanClass){
		setters = new ArrayList<Method>();
		Method[] methods = beanClass.getDeclaredMethods();
		for(Method m:methods){
			if(m.getName().startsWith("set")){
				setters.add(m);
			}
		}
	}
	
	public void callSetter(Object obj, ResultSet rs){
		for(Method m:setters){
			String type = m.getParameterTypes()[0].getName(); // get the param type (setters only have 1)
			String methodName = "get"+capitalizeFirstLetter(type); 
			String item = camelToUnderscore(m.getName().replace("set", ""));
			try{
				Method get = rs.getClass().getMethod(methodName,String.class);
				m.invoke(obj, get.invoke(rs, item));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	// Convert camel case to underscore case
	public String camelToUnderscore(String s){
		String regex = "([a-z0-9])([A-Z])";
		String rep = "$1_$2";
		return s.replaceAll(regex, rep).toLowerCase();
	}
	
	// Capitalize the first letter
	public String capitalizeFirstLetter(String s){
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
}
