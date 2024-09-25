import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;

public class CSVHelper {
	/**
	 * Creates a CSVReader object for reading from a CSV file.
	 *
	 * @param filename The name of the CSV file to read from. Path starts from root
	 *                 directory of the project.
	 * @return A CSVReader object for reading from the specified file, or null if an
	 *         error occurs.
	 * @throws Exception If an error occurs while creating the CSVReader.
	 */
	public static CSVReader createCSVReader(String filename) {
		try {
			CSVReader reader = new CSVReader(new FileReader(filename));
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates a CSVWriter object for writing to a CSV file.
	 *
	 * @param filename The name of the CSV file to write to. Path starts from root
	 *                 directory of the project.
	 * @return A CSVWriter object for writing to the specified file, or null if an
	 *         error occurs.
	 * @throws Exception If an error occurs while creating the CSVWriter.
	 */
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
