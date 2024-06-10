package th.co.locus.jlo.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Base64Utils;

public class FileUtil {

	public static InputStream readInputStream(String base64File) {
		byte[] fileBytes  = Base64Utils.decodeFromString(base64File.substring(base64File.indexOf(",")+1));
		return new ByteArrayInputStream(fileBytes);
	}

	public static HashMap<Integer, List<String>> readExcelDataByFilePath(InputStream inputStream) throws Exception {
		HashMap<Integer, List<String>> map = new HashMap<>();
		
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
		}catch(Exception e) {
			workbook = new HSSFWorkbook(inputStream);
		}
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		DataFormatter dataFormatter = new DataFormatter();
		
	    while (sheetIterator.hasNext()) {
	        Sheet sheet = sheetIterator.next();
	        sheet.forEach(row -> {
	        	List<String> listString = new ArrayList<String>();
	            row.forEach(cell -> {
	                listString.add(dataFormatter.formatCellValue(cell));
	            });
	            map.put(row.getRowNum(), listString);
	        });
	    }
	    
	    if(workbook!=null)workbook.close();
		return map;
	}
}
