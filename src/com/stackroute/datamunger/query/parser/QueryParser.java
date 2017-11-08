package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import java.util.regex.Pattern;

public class QueryParser {

	QueryParameter queryParameter = new QueryParameter();

	/*
	 * this method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {

		queryParameter.setQueryString(getQueryString(queryString));
		
		// * extract the name of the file from the query.
		queryParameter.setFile(getFile(queryString));
				
		queryParameter.setBaseQuery(getBaseQuery(queryString));
				
		// * extract the order by fields from the query string. Please note that we will
		queryParameter.setOrderByFields(getOrderByFields(queryString));

		
		// * extract the group by fields from the query string. Please note that we will
		queryParameter.setGroupByFields(getGroupByFields(queryString));

		
		//* extract the selected fields from the query string. Please note that we will
		queryParameter.setFields(getFields(queryString));

		
		// * extract the conditions from the query string(if exists). for each condition,
		queryParameter.setRestrictions(getRestrictions(queryString));		

		// * extract the logical operators(AND/OR) from the query, if at all it is
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));

		// * extract the aggregate functions from the query. The presence of the aggregate
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		
				 
		return queryParameter;
	}

	public String getQueryString(String queryString) {
		String query = queryString.toLowerCase().trim();
		return query;
	}

	public String getBaseQuery(String queryString) {

		String base = queryString.toLowerCase();
		base = queryString.split("where|group by|order by")[0].trim();
		return base;
	}

	public String getFile(String queryString) {

		String file = queryString.toLowerCase();
		if (Pattern.matches("(.*where.*)|(.*group by.*)|(.*order by.*)", file)) {
			file = file.split("from")[1].split("where|group|order")[0].trim();
			return file;
		} else {
			file = file.split("from")[1].trim();
			return file;
		}
	}

	public List<String> getOrderByFields(String queryString) {

		String o = queryString.toLowerCase().trim();
		if (Pattern.matches("(.*order by.*)", o)) {
			o = o.split("order by")[1].trim();
			List<String> order = new ArrayList<String>(Arrays.asList(o.split(",")));
			return order;
		} else {
			return null;
		}
	}

	public List<String> getGroupByFields(String queryString) {

		String groupby = queryString.toLowerCase().trim();
		List<String> group = new ArrayList<String>();
		if (Pattern.matches("(.*group by.*)(.*order by.*)", groupby)) {
			groupby = groupby.split("group by")[1].trim().split("order by")[0].trim();
		} else if (Pattern.matches("(.*group by.*)", groupby)) {
			groupby = groupby.split("group by")[1].trim();
		} else {
			return null;
		}
		group = Arrays.asList(groupby.split(","));
		return group;
	}

	public List<String> getFields(String queryString) {

		String field = queryString.toLowerCase().trim();
		List<String> fields = new ArrayList<String>();
		field = field.toLowerCase();
		field = field.split("select")[1].split("from")[0].trim();
		/*if (field.contains("*")) {
			return ;
		} else {*/
			String[] f = field.split(",");
			for(String s: f) {
				fields.add(s.trim());
			}
			return fields;
		}

	public List<String> getLogicalOperators(String queryString) {

		String operators = Conditions(queryString);
		List<String> operator = new ArrayList<String>();
		if (operators != null) {
			String[] ope = operators.split("\\s+");
			for (String s : ope) {
				if (Pattern.matches("(and)|(or)", s)) {
					operator.add(s);
				}
			}
			return operator;
		} else {
			return null;
		}
	}

	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		List<AggregateFunction> a = new ArrayList<AggregateFunction>();
		
		String aggre = Aggr(queryString);
		if(aggre!= null) {
		String[] q = aggre.split(",");
		for (int i = 0; i < q.length; i++) {
			AggregateFunction af = new AggregateFunction();
			af.setField(q[i].trim());
			af.setFunction(q[i].trim());
			a.add(af);
		}
		return a;
	}else {
		return null;
	}
	}

	public String Aggr(String queryString) {

		if (Pattern.matches("(select \\*.*)", queryString)) {
			return null;
		} else {
			String agg = queryString.split("select")[1].trim().split("from")[0];
			if (agg != null) {
				return agg;
			} else {
				return null;
			}
		}
	}

	public List<Restriction> getRestrictions(String queryString) {
		List<Restriction> r = new ArrayList<Restriction>();
		
		String restrict = Conditions(queryString);
		
		if(restrict!=null) {
		String[] restrictions = restrict.split("and|or ");
		for (int i = 0; i < restrictions.length; i++) {
			Restriction res = new Restriction();
			res.setPropertyValue(restrictions[i].trim().split(">=|<=|<|>|!=|=|!=")[1].trim());
			System.out.println("Value "+res.getPropertyValue());
			res.setCondition(restrictions[i].trim().replaceAll("[a-zA-Z0-9\'\\_]", "").trim());
			System.out.println("Condition "+res.getCondition());
			res.setPropertyName(restrictions[i].trim().split(">=|<=|<|>|!=|=")[0].trim());
			System.out.println("Name "+res.getPropertyName());
			r.add(res);
		}
		System.out.println();
		return r;
	}else {
		return null;
	}
	}

	public String Conditions(String queryString) {
		String conditions = null;
		//queryString = queryString.toLowerCase();
		if (Pattern.matches("(.*where.*)(.*group.*)(.*order.*)", queryString)) {
			conditions = queryString.split("where")[1].trim().split("group by|order by")[0].trim();
		} else if (Pattern.matches("(.*where.*)", queryString)) {
			conditions = queryString.split("where")[1].trim();
		} else {
			return null;
		}
		
		return conditions;
	}
	
	
}
