package JavaFinalProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	   
	   public ArrayList<String> getData(InputStream is) throws IOException {
	      ArrayList<String> values = new ArrayList<String>();
	      
	      try (InputStream inp = is) {
	  
		      Workbook wb = WorkbookFactory.create(inp);
		      Sheet sheet = wb.getSheetAt(0);
		      Iterator<Row> iterator = sheet.iterator();
		     
		      while(iterator.hasNext()) {
		        	String value = "";
		        	String lastValue = "";
		        	
		        	Row row = iterator.next();
		        	
		        	Iterator<Cell> cell = row.iterator();
		        	
		        	while(cell.hasNext()) {
		                
		                Cell currentCell = cell.next();
		                
		                switch (currentCell.getCellType()){
		                  case FORMULA:
		                      value = currentCell.getCellFormula();
		                      break;
		                  case NUMERIC:
		                      value = currentCell.getNumericCellValue()+"";
		                      break;
		                  case STRING:
		                      value = currentCell.getStringCellValue()+"";
		                      break;
		                  case BLANK:
		                      value = "";
		                      break;
		                  case ERROR:
		                      value = currentCell.getErrorCellValue()+"";
		                      break;
		                  default:
		                      value = new String();
		                      break;
		                  }     		
		        		lastValue += value + "///";
	
		        	}
		        	
		        	values.add(lastValue);

		        }}catch (FileNotFoundException e) {
		    	    // TODO Auto-generated catch block
		    	    e.printStackTrace();
		    	    } catch (IOException e) {
		    	    // TODO Auto-generated catch block
		    	    e.printStackTrace();
		    	    }

	      
	   
	      
	      return values;
	}

}
