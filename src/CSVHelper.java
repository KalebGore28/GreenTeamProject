import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVHelper {

	/**
	 * Searches for a specific key in the header row of a CSV file.
	 *
	 * @param filePath the path to the CSV file to be read
	 * @param key      the key to search for in the header row
	 * @return true if the key is found in the header row, false otherwise
	 */
	public static boolean findKey(String filePath, String key) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Store the header row
			String[] headers = reader.readNext();
			for (String header : headers) {
				if (header.equals(key)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Reads a single line from the CSV file as a map.
	 * 
	 * @param filePath The path to the CSV file.
	 * @param rowId    The row ID to read.
	 * @return A map representing the row, or null if not found or an error occurs.
	 */
	public static Map<String, String> readLineAsMap(String filePath, int rowId) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Store the header row
			String[] headers = reader.readNext();

			// Read the data rows
			String[] data;
			int currentRow = 1; // Start from row 1
			while ((data = reader.readNext()) != null) {

				// Check if the current row matches the requested row ID
				if (currentRow == rowId) {

					// Create a map of the row data
					Map<String, String> map = new LinkedHashMap<>();
					for (int i = 0; i < headers.length; i++) {
						map.put(headers[i], data[i]);
					}
					return map;
				}
				currentRow++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Searches for a line in the CSV file that matches the given criteria.
	 * 
	 * @param filePath The path to the CSV file.
	 * @param column   The column to search.
	 * @param value    The value to search for.
	 * @return A map representing the matching row, or null if not found or an error
	 *         occurs.
	 */
	public static Map<String, String> searchLineAsMap(String filePath, String column, String value) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Store the header row
			String[] headers = reader.readNext();

			// Read the data rows
			String[] data;
			while ((data = reader.readNext()) != null) {

				// Iterate through the columns of the current row
				for (int i = 0; i < headers.length; i++) {

					// Check if the current column matches the requested column and value
					if (headers[i].equals(column) && data[i].equals(value)) {

						// Create a map of the row data
						Map<String, String> map = new LinkedHashMap<>();
						for (int j = 0; j < headers.length; j++) {
							map.put(headers[j], data[j]);
						}
						return map;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads multiple lines from the CSV file as a list of maps.
	 * 
	 * @param filePath  The path to the CSV file.
	 * @param startLine The starting line (inclusive).
	 * @param endLine   The ending line (inclusive).
	 * @return A list of maps representing the rows, or null if an error occurs.
	 */
	public static List<Map<String, String>> readLinesAsMap(String filePath, int startLine, int endLine) {

		List<Map<String, String>> lines = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Store the header row
			String[] headers = reader.readNext();

			// Read the data rows
			String[] data;
			int currentRow = 1; // Start from row 1
			while ((data = reader.readNext()) != null) {

				// Check if the current row is within the requested range
				if (currentRow >= startLine && currentRow <= endLine) {

					// Create a map of the row data
					Map<String, String> map = new LinkedHashMap<>();
					for (int i = 0; i < headers.length; i++) {
						map.put(headers[i], data[i]);
					}
					lines.add(map);
				}
				currentRow++;
			}
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Searches for multiple lines in the CSV file that match the given criteria.
	 * 
	 * @param filePath The path to the CSV file.
	 * @param column   The column to search.
	 * @param value    The value to search for.
	 * @param limitSearch The maximum number of lines to return.
	 * @return A list of maps representing the matching rows, or null if an error
	 *         occurs.
	 */
	public static List<Map<String, String>> searchLinesAsMap(String filePath, String column, String value, int limitSearch) {
		List<Map<String, String>> lines = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Store the header row
			String[] headers = reader.readNext();

			// Read the data rows
			String[] data;
			while ((data = reader.readNext()) != null && lines.size() < limitSearch) {

				// Iterate through the columns of the current row
				for (int i = 0; i < headers.length; i++) {

					// Check if the current column matches the requested column and value
					if (headers[i].equals(column) && data[i].equals(value)) {

						// Create a map of the row data
						Map<String, String> map = new LinkedHashMap<>();
						for (int j = 0; j < headers.length; j++) {
							map.put(headers[j], data[j]);
						}
						lines.add(map);
						break; // Move to the next row after finding a match
					}
				}
			}
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Replaces a line in the CSV file with the given data.
	 * 
	 * @param filePath The path to the CSV file.
	 * @param rowId    The row ID to replace.
	 * @param data     The data to replace the row with.
	 * @return True if the operation was successful, false otherwise.
	 */
	public static boolean writeReplaceLine(String filePath, String[] data, int rowId) {
		List<String[]> lines = new ArrayList<>();

		// Store the existing data
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] line;
			while ((line = reader.readNext()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// Write the new data
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePath),
				CSVWriter.DEFAULT_SEPARATOR,
				CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END)) {
			for (int i = 0; i < lines.size(); i++) {

				// Check if the current row matches the requested row ID
				if (i == rowId) {

					// Add rowId to the data first
					String[] dataWithRowId = new String[data.length + 1];
					dataWithRowId[0] = String.valueOf(rowId);
					for (int j = 0; j < data.length; j++) {
						dataWithRowId[j + 1] = data[j];
					}

					// Write the new data
					writer.writeNext(dataWithRowId);
				} else {

					// Write the existing data
					writer.writeNext(lines.get(i));
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		String emp[] = { "100", "John", "Doe", "john.doe@example.com", "Engineering", "Software Engineer", "75000" };

		if (writeReplaceLine("src/databases/employees.csv", emp, 1)) {
			System.out.println("Employee record replaced successfully.");
		} else {
			System.out.println("Failed to replace employee record.");
		}

	}
}