package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import log.Logger;
import log.MessageType;

/**Class for loading csv files.
 * Reads only {@link #MAX_LINES_TO_READ} lines.
 * 
 * @author Johannes
 *
 */
public class CsvFileLoader {

	/** Csv file to read*/
	private File file;
	/** ArrayList of lines of csv file*/
	private ArrayList<ArrayList<String>> csvTable;
	/** Size of the longest line in the csv file (number of columns with content)*/
	private int maxLineSize;
	/** Number of lines which are read*/
	public static int MAX_LINES_TO_READ = 200; 
	
	public CsvFileLoader(File file) {
		this.file = file;
		csvTable = new ArrayList<ArrayList<String>>();
		maxLineSize = 0;
	}
	
	public boolean load(){
		if(!file.exists())
			return false;
			
		FileReader fr;
		
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			Logger.log(MessageType.ERROR, file.getAbsolutePath() +" not found!");
			return false;
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String currentLine;
		int lineNr = 0;
		
		try {
			while((currentLine = reader.readLine())!=null){
				
				if(lineNr > MAX_LINES_TO_READ)
					break;
				
				String[] splitLine = currentLine.split(",");
				// calculate longest line
				if(splitLine.length > maxLineSize){
					maxLineSize = splitLine.length;
				}
				// add line to table
				for(int i=0; i<splitLine.length; ++i){
					csvTable.add(new ArrayList<String>());
					csvTable.get(lineNr).add(splitLine[i]);
				}
				
				lineNr++;
			}
			
			reader.close();
		} catch (IOException e) {
			Logger.log(MessageType.ERROR, "Error while reading " + file.getAbsolutePath() + " !");
			return false;
		}
		
		Logger.log(MessageType.NOTIFICATION, "Successfully loaded " + file.getAbsolutePath() + " !");
		
		return true;
		
	}
	
	/** Returns the size of the longest line in the csv file*/
	public int getMaxLineSize() {
		return maxLineSize;
	}
	
	/** Returns the content of the csv file*/
	public ArrayList<ArrayList<String>> getCsvTable() {
		return csvTable;
	}

	public File getFile() {
		return file;
	}
}
