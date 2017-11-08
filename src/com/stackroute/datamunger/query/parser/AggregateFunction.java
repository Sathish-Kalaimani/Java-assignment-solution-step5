package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {
	
	private String function;
	private String field;

	public String getFunction() {
		return function;
	}
	
	public void setFunction(String func) {
		function = func;
	}

	public String getField() {
		return field;
	}
	
	public void setField(String f) {
		field = f;
	}
	
	

}
