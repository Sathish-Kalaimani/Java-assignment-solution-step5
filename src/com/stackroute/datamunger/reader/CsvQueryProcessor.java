package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;



public class CsvQueryProcessor implements QueryProcessingEngine {
	
	public DataSet getResultSet(QueryParameter queryParameter) throws FileNotFoundException {
		
		String file =null;
		RowDataTypeDefinitions RowData = new RowDataTypeDefinitions();
		String[] headers = null;
		String[] firstRowValues = null;
		String rowValue = null;
		String[] rowValues = null;
		BufferedReader reader = null;
		Filter filter = new Filter();
		Row row = new Row();
		DataSet dataSet = new DataSet();
		Header header = new Header();
		long rowid = 1;
		
		reader = new BufferedReader(new FileReader(queryParameter.getFile()));	
		try {
		headers = reader.readLine().split("\\s*,\\s*");
		file = reader.readLine().toLowerCase();  
		//firstRowValues = reader.readLine().toLowerCase().split("\\s*,\\s*", -1);
		
		for(int i = 0; i < headers.length; i++) {
			
		header.put(headers[i], i);
		firstRowValues = DataTypeDefinitions.getDatatypes(file);
			RowData.put(i, firstRowValues[i]);
			
		 } 
		reader.close();
		}catch (IOException e) {
	        }
		
		try{
		boolean isSelected;
			
		reader = new BufferedReader(new FileReader(queryParameter.getFile()));
			
			 reader.readLine();
			 while ((rowValue = reader.readLine()) != null) {
				 rowValues = rowValue.split("\\s*,\\s*", -1);
	             isSelected = true;
	             if (queryParameter.getRestrictions() != null) {
	                    int iteration = 0;
	              
	                    for (Restriction restriction : queryParameter.getRestrictions()) {
	                    	System.out.println(restriction.getPropertyValue());
	                        if (iteration == 0) {
	                            isSelected = filter.evaluateExpression(restriction.getCondition(),rowValues[header.get(restriction.getPropertyName())],restriction.getPropertyValue(),RowData.get(header.get(restriction.getPropertyName())));
	                            iteration++;
	                            continue;
	                        }
	                       
	                        if (queryParameter.getLogicalOperators().get(iteration - 1) != null) {
	                            if (queryParameter.getLogicalOperators().get(iteration - 1).equalsIgnoreCase("and")) {
	                                isSelected = isSelected && filter.evaluateExpression(restriction.getCondition(),
	                                        rowValues[header.get(restriction.getPropertyName())],
	                                        restriction.getPropertyValue(),
	                                        RowData.get(header.get(restriction.getPropertyName())));
	                            } else if (queryParameter.getLogicalOperators().get(iteration - 1).equalsIgnoreCase("or")) {
	                                isSelected = isSelected || filter.evaluateExpression(restriction.getCondition(),
	                                        rowValues[header.get(restriction.getPropertyName())],
	                                        restriction.getPropertyValue(),
	                                        RowData.get(header.get(restriction.getPropertyName())));
	                            }
	                        }
	                    }
	                }
	                if (isSelected) {
	                    row = new Row();
	                    List<String> selectColumns = queryParameter.getFields();
	                    for (String selectColumn : selectColumns) {
	                        // check if all columns are required
	                        if (selectColumn.equals("*")) {
	                            for (int i = 0; i < rowValues.length; i++)
	                                row.put(headers[i], rowValues[i]);
	                            break;
	                        } else {
	                            row.put(headers[header.get(selectColumn)], rowValues[header.get(selectColumn)]);
	                        }
	                    }
	                    dataSet.put(rowid, row);
	                    rowid++;
	                }
	            }
			 //reader.close();
		 } catch (NumberFormatException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            
	        }
		return dataSet;
	}	
	
}
