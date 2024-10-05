import com.opencsv.CSVReader;
// import com.opencsv.CSVWriter;

import java.io.FileReader;
// import java.io.FileWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
	private final static int OFFSET = 1; // Offset to account for header row

	/**
	 * Searches for a specific key in the header row of a CSV file.
	 *
	 * @param filePath the path to the CSV file to be read
	 * @param key      the key to search for in the header row
	 * @return true if the key is found in the header row, false otherwise
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	public static boolean findKey(String filePath, String key) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Read the header row
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

	// Read Line Methods

	/**
	 * Reads a specific line from a CSV file.
	 *
	 * @param filePath The path of the CSV file to read from. Path starts from root
	 *                 directory of the project.
	 * @param line     The line number to read (1-based index).
	 * @return An array of Strings representing the cells in the specified line, or
	 *         null if an error occurs.
	 */
	public static String[] readLineAsStrings(String filePath, int line) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Read the specified line
			String[] data = null;
			for (int i = 0; i < line + OFFSET; i++) {
				data = reader.readNext();
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a specific line from a CSV file using the header row values as keys.
	 *
	 * @param filePath The path of the CSV file to read from. Path starts from root
	 *                 directory of the project.
	 * @param line     The line number to read (1-based index).
	 * @return A Map where the keys are the header values and the values are the
	 *         corresponding cell values from the specified line, or null if an
	 *         error occurs.
	 */
	public static Map<String, String> readLineAsMap(String filePath, int line) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			// Read the header row and store it in an array
			String[] headers = reader.readNext();

			// Read the specified line
			String[] data = null;
			for (int i = 0; i < line; i++) {
				data = reader.readNext();
			}

			// Store the data in a map
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < headers.length; i++) {
				map.put(headers[i], data[i]);
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Read Lines Methods

	/**
	 * Reads a specified range of lines from a CSV file and returns them as a 2D
	 * array of strings.
	 *
	 * @param filePath  the path to the CSV file.
	 * @param startLine the starting line number (0-based index) to read from.
	 * @param endLine   the ending line number (0-based index) to read to.
	 * @return a 2D array of strings, each representing a line from the CSV file
	 *         within the specified range,
	 *         or null if an exception occurs.
	 */
	public static String[][] readLinesAsStrings(String filePath, int startLine, int endLine) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Skips the reader to the start line
			String[] data = null;
			for (int i = 0; i < startLine + OFFSET; i++) {
				data = reader.readNext();
			}

			// Read the specified lines
			String[][] lines = new String[endLine - startLine + 1][];
			for (int i = 0; i < lines.length; i++) {
				lines[i] = data;
				data = reader.readNext();
			}
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a specified range of lines from a CSV file and returns them as an array
	 * of maps.
	 * Each map represents a line in the CSV file, with the keys being the column
	 * headers and the values being the corresponding data.
	 *
	 * @param filePath  the path to the CSV file
	 * @param startLine the line number to start reading from (0-based index)
	 * @param endLine   the line number to stop reading at (inclusive, 0-based
	 *                  index)
	 * @return an array of maps, where each map represents a line in the CSV file
	 *         with column headers as keys and corresponding data as values,
	 *         or {@code null} if an exception occurs
	 */
	public static Map<String, String>[] readLinesAsMap(String filePath, int startLine, int endLine) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			// Read the header row and store it in an array
			String[] headers = reader.readNext();

			// Skip to the start line
			String[] data = null;
			for (int i = 0; i < startLine; i++) {
				data = reader.readNext();
			}

			// Read the specified lines
			@SuppressWarnings("unchecked")
			Map<String, String>[] lines = new Map[endLine - startLine + 1];
			for (int i = 0; i < lines.length; i++) {
				lines[i] = new HashMap<>();
				for (int j = 0; j < headers.length; j++) {
					lines[i].put(headers[j], data[j]);
				}
				data = reader.readNext();
			}

			return lines;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Search Line Methods

	/**
	 * Searches for a specific key-value pair in a CSV file.
	 *
	 * @param filePath The path of the CSV file to read from. Path starts from root
	 *                 directory of the project.
	 * @param key      The key to search for in the header row.
	 * @param value    The value to search for in the specified column.
	 * @return An array of Strings representing the cells in the line where the key
	 *         matches the specified value, or null if an error occurs.
	 */
	public static String[] searchLineAsStrings(String filePath, String key, String value) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Find the index of the key
			String[] headers = reader.readNext();
			int index = -1;
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].equals(key)) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				return null;
			}

			// Search for the specified value
			String[] data;
			while ((data = reader.readNext()) != null) {
				if (data[index].equals(value)) {
					return data;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Searches for a specific key-value pair in a CSV file using the header row
	 * values as keys.
	 *
	 * @param filePath The path of the CSV file to read from. Path starts from root
	 *                 directory of the project.
	 * @param key      The key to search for in the header row.
	 * @param value    The value to search for in the specified column.
	 * @return A Map where the keys are the header values and the values are the
	 *         corresponding cell values from the line where the key matches the
	 *         specified value, or null if an error occurs.
	 */
	public static Map<String, String> searchLineAsMap(String filePath, String key, String value) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			// Read the header row and store it in an array
			String[] headers = reader.readNext();

			// Find the index of the key
			int index = -1;
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].equals(key)) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				return null;
			}

			// Search for the specified value
			String[] data;
			while ((data = reader.readNext()) != null) {
				if (data[index].equals(value)) {
					// Store the data in a map
					Map<String, String> map = new HashMap<>();
					for (int i = 0; i < headers.length; i++) {
						map.put(headers[i], data[i]);
					}
					return map;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Search Lines Methods

	/**
	 * Searches for lines in a CSV file that match a specified key-value pair and
	 * returns them as a 2D array of strings.
	 *
	 * @param filePath       the path to the CSV file to be searched.
	 * @param key            the header key to search for in the CSV file.
	 * @param value          the value to match against the specified key.
	 * @param maxSearchLines the maximum number of lines to search before stopping.
	 * @return a 2D array of strings containing the matching lines, or null if the
	 *         key is not found or an error occurs.
	 */
	public static String[][] searchLinesAsStrings(String filePath, String key, String value, int maxSearchLines) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			// Find the index of the key
			String[] headers = reader.readNext();
			int index = -1;
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].equals(key)) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				return null;
			}

			// Temporarily store matching lines
			List<String[]> tempLines = new ArrayList<>();
			String[] data;
			while ((data = reader.readNext()) != null && tempLines.size() < maxSearchLines) {
				if (data[index].equals(value)) {
					tempLines.add(data);
				}
			}

			// Convert the list to a 2D array
			String[][] lines = new String[tempLines.size()][];
			for (int i = 0; i < tempLines.size(); i++) {
				lines[i] = tempLines.get(i);
			}
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Searches for lines in a CSV file that match a specified key-value pair and
	 * returns them as an array of maps.
	 *
	 * @param filePath       the path to the CSV file to be searched.
	 * @param key            the header key to search for in the CSV file.
	 * @param value          the value to match against the specified key.
	 * @param maxSearchLines the maximum number of lines to search before stopping.
	 * @return an array of maps containing the matching lines, or null if the key is
	 *         not found or an error occurs.
	 */
	public static Map<String, String>[] searchLinesAsMap(String filePath, String key, String value,
			int maxSearchLines) {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			// Read the header row and store it in an array
			String[] headers = reader.readNext();

			// Find the index of the key
			int index = -1;
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].equals(key)) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				return null;
			}

			// Temporarily store matching lines
			List<Map<String, String>> tempLines = new ArrayList<>();
			String[] data;
			while ((data = reader.readNext()) != null && tempLines.size() < maxSearchLines) {
				if (data[index].equals(value)) {
					// Store the data in a map
					Map<String, String> map = new HashMap<>();
					for (int i = 0; i < headers.length; i++) {
						map.put(headers[i], data[i]);
					}
					tempLines.add(map);
				}
			}

			// Convert the list to an array
			@SuppressWarnings("unchecked")
			Map<String, String>[] lines = new Map[tempLines.size()];
			for (int i = 0; i < tempLines.size(); i++) {
				lines[i] = tempLines.get(i);
			}
			return lines;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}