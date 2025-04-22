package Generic_Utilies;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel_Utility {
	
	
	public String FetchdatafromExcel(String sheetname,int rowindex, int cellindex) throws EncryptedDocumentException, IOException {
	
	FileInputStream fis = new FileInputStream("D:\\OneDrive\\Desktop\\Org_Con.xlsx");
	Workbook wb = WorkbookFactory.create(fis);
	Sheet s=wb.getSheet(sheetname);
	Row r = s.getRow(rowindex);
	Cell c = r.getCell(cellindex);
	String data = c.toString();
	wb.close();
	return data;
	
	}
	/*public String FetchMutipledatafromExcel(String sheetname,int rowindex, int cellindex) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("D:\\OneDrive\\Desktop\\Org_Con.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet s=wb.getSheet(sheetname);
		String data = null;
		for(int i=0;i<=s.getFirstRowNum();i++) {
			for(int j=0;j<s.getRow(i).getLastCellNum();j++) {
				
				data=s.getRow(i).getCell(j).toString();
				return data;
			}
		}
		
	}*/
      
	
	public void WriteBackdatatoExcel(String sheetname,int rowindex, int cellindex) throws EncryptedDocumentException, IOException {
		
		FileInputStream fis = new FileInputStream("D:\\OneDrive\\Desktop\\Org_Con.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet s=wb.getSheet(sheetname);
		Row r = s.getRow(rowindex);
		Cell c = r.getCell(cellindex);
		c.setCellValue(cellindex);
		
		FileOutputStream fos = new FileOutputStream("D:\\OneDrive\\Desktop\\Org_Con.xlsx");
		wb.write(fos);
		wb.close();
		
		
	}
	
	
	
}
