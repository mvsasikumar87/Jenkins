package utils;

import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelReader {
	
	public static XSSFSheet workSheet;
	public static XSSFWorkbook workBook;
	public static String filePath;
	public static String[] userData=new String[6];
	
	public static String[] readExcelData(String fileName, String sheetName) throws Exception
	{
		
        FileInputStream workFile = new FileInputStream(fileName);
			workBook = new XSSFWorkbook(workFile);
			workSheet = workBook.getSheet(sheetName);
			XSSFRow row = workSheet.getRow(0);

			if (row == null) {
				return null; // or handle it as needed
			}

			for (int i = 0; i < 10; i++) {
				XSSFCell cell = row.getCell(i);
				if (cell != null) {
					CellType c = cell.getCellType();
					switch (c) {
					case STRING:
						userData[i] = cell.getStringCellValue();
						break;

					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {	 	  	      	 	    	   	 	       	 	
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							userData[i] = dateFormat.format(cell.getDateCellValue());
						} else {
							userData[i] = String.valueOf((long) cell.getNumericCellValue());
						}
						break;

					case FORMULA:
						if (DateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							userData[i] = dateFormat.format(cell.getDateCellValue());
						}
						break;

					case BLANK:
						userData[i] = ""; // or handle it as needed
						break;
					}
//					System.out.println("Value at index " + i + ": " + userData[i]);
				}
			}
        return userData; // (placeholder for actual implementation)
	
	}
}


