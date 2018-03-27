package org.mmu.g4sm.qa.at.selenium.utils;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;



public class CsvFactory {
	
	public  String path;
	CSVWriter writer;
	

	public CsvFactory(String csvPath) {
		this.path= System.getProperty("user.dir") + csvPath;
		deleteCsvFileIfExists(path);
	}
	
	private void deleteCsvFileIfExists(String path2) {
		File f = new File(path2);
		if(f.exists() && !f.isDirectory()) { 
		    f.delete();
		}	
	}

	public void InsertDataToCsvFromList(List<String[]> listOfStringArray) {
		try {
			writer = new CSVWriter(new FileWriter(path));
			writer.writeAll(listOfStringArray);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


}
