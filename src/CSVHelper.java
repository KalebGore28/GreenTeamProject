import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;

public class CSVHelper {
	public static CSVReader createCSVReader(String filename) {
		try {
			CSVReader reader = new CSVReader(new FileReader(filename));
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static CSVWriter createCSVWriter(String filename) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(filename));
			return writer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
