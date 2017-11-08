package com.stackroute.datamunger.query;

//this class contains methods to evaluate expressions
public class Filter {

	/*
	 * the evaluateExpression() method of this class is responsible for evaluating
	 * the expressions mentioned in the query. It has to be noted that the process
	 * of evaluating expressions will be different for different data types. there
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method
	 * should be able to evaluate all of them. Note: while evaluating string
	 * expressions, please handle uppercase and lowercase
	 * 
	 */

	public boolean evaluateExpression(String operator, String firstInput, String secondInput, String dataType) {

		switch (operator) {

		case "=":
			if (equalTo(firstInput, secondInput, dataType))
				return true;
			else
				return false;

		case "!=":
			if (!equalTo(firstInput, secondInput, dataType))
				return true;
			else
				return false;

		case "<=":
			if (lessThanOrEqualTo(firstInput, secondInput, dataType))
				return true;
			else
				return false;

		case ">=":
			if (greaterThanOrEqualTo(firstInput, secondInput, dataType))
				return true;
			else
				return false;

		case "<":
			if (lessThan(firstInput, secondInput, dataType))
				return true;
			else
				return false;

		case ">":
			if (greaterThan(firstInput, secondInput, dataType))
				return true;
			else
				return false;

		}

		return false;
	}

	public boolean greaterThan(String firstInput, String secondInput, String dataType) {
		switch (dataType) {
		case "java.lang.Integer":
		case "java.lang.Double":
			try {
				if (Double.parseDouble(firstInput) > Double.parseDouble(secondInput))
					return true;
				else
					return false;
			} catch (NumberFormatException nfe) {
				return false;
			}
		default:
			if ((firstInput.compareToIgnoreCase(secondInput)) > 0)
				return true;
			else
				return false;
		}
	}

	public boolean lessThan(String firstInput, String secondInput, String dataType) {
		switch (dataType) {
		case "java.lang.Integer":
		case "java.lang.Double":
			try {
				if (Double.parseDouble(firstInput) < Double.parseDouble(secondInput))
					return true;
				else
					return false;
			} catch (NumberFormatException nfe) {
				return false;
			}
		default:
			if ((firstInput.compareToIgnoreCase(secondInput)) < 0)
				return true;
			else
				return false;
		}
	}

	public boolean greaterThanOrEqualTo(String firstInput, String secondInput, String dataType) {
		switch (dataType) {
		case "java.lang.Integer":
		case "java.lang.Double":
			try {
				if (Double.parseDouble(firstInput) >= Double.parseDouble(secondInput))
					return true;
				else
					return false;
			} catch (NumberFormatException nfe) {
				return false;
			}
		default:
			if ((firstInput.compareToIgnoreCase(secondInput)) >= 0)
				return true;
			else
				return false;
		
		}
		
	}

	public boolean lessThanOrEqualTo(String firstInput, String secondInput, String dataType) {
		switch (dataType) {
		case "java.lang.Integer":
		case "java.lang.Double":
			try {
				if (Double.parseDouble(firstInput) <= Double.parseDouble(secondInput))
					return true;
				else
					return false;
			} catch (NumberFormatException nfe) {
				return false;
			}
		default:
			if ((firstInput.compareToIgnoreCase(secondInput)) <= 0)
				return true;
			else
				return false;
		}
	}

	public boolean equalTo(String firstInput, String secondInput, String dataType) {
		switch (dataType) {
		case "java.lang.Integer":
		case "java.lang.Double":
			try {
				if (Double.parseDouble(firstInput) == Double.parseDouble(secondInput))
					return true;
				else
					return false;
			} catch (NumberFormatException nfe) {
				return false;
			}
		default:
			if ((firstInput.compareToIgnoreCase(secondInput)) == 0)
				return true;
			else
				return false;
		}
	}

	// method containing implementation of equalTo operator

	// method containing implementation of notEqualTo operator

	// method containing implementation of greaterThan operator

	// method containing implementation of greaterThanOrEqualTo operator

	// method containing implementation of lessThan operator

	// method containing implementation of lessThanOrEqualTo operator

}
