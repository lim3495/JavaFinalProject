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
	      String value="";
	      
	      try (InputStream inp = is) {
	  
		      Workbook wb = WorkbookFactory.create(inp);
		      Sheet sheet = wb.getSheetAt(0);
		      Iterator<Row> iterator = sheet.rowIterator();
		     
		     while(iterator.hasNext()) {
		         Row currentRow = iterator.next();
		         
		         Iterator<Cell> cellIterator = currentRow.iterator();
		         
		         while(cellIterator.hasNext()) {
		            Cell currentCell = cellIterator.next();
		            
		            value = currentCell.getStringCellValue();
		            
		            values.add(value);
		         }
		      }

	      
	   }
	      
	      return values;
	}

}
