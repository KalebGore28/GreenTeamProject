import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CSVHelperTest {
	@Test
	public void testFindKey() {
		assertEquals(true, CSVHelper.findKey("src/databases/employees.csv", "employee_id"));
		assertEquals(false, CSVHelper.findKey("src/databases/employees.csv", "employee_id2"));
	}

	// Read Line Tests

	@Test
	public void testReadLineRetrieval() {
		Map<String, String> line = CSVHelper.readLineAsMap("src/databases/employees.csv", 1);

		assertNotNull(line);

		assertEquals("1", line.get("id"));
		assertEquals("100", line.get("employee_id"));
		assertEquals("John", line.get("first_name"));
		assertEquals("Doe", line.get("last_name"));
		assertEquals("john.doe@example.com", line.get("email"));
		assertEquals("Engineering", line.get("department"));
		assertEquals("Software Engineer", line.get("position"));
		assertEquals("75000.0", line.get("salary"));
	}

	@Test
	public void testReadLineNonExistent() {
		Map<String, String> line = CSVHelper.readLineAsMap("src/databases/employees.csv", 100);

		assertEquals(null, line);
	}

	@Test
	public void testReadLineInvalid() {
		Map<String, String> line = CSVHelper.readLineAsMap("src/databases/employees.csv", -1);

		assertEquals(null, line);
	}

	// Read Lines Tests

	@Test
	public void testReadLinesRetrieval() {
		List<Map<String, String>> lines = CSVHelper.readLinesAsMap("src/databases/employees.csv", 1, 2);

		assertNotNull(lines);
		assertEquals(2, lines.size());

		assertEquals("1", lines.get(0).get("id"));
		assertEquals("100", lines.get(0).get("employee_id"));
		assertEquals("John", lines.get(0).get("first_name"));
		assertEquals("Doe", lines.get(0).get("last_name"));
		assertEquals("john.doe@example.com", lines.get(0).get("email"));
		assertEquals("Engineering", lines.get(0).get("department"));
		assertEquals("Software Engineer", lines.get(0).get("position"));
		assertEquals("75000.0", lines.get(0).get("salary"));

		assertEquals("2", lines.get(1).get("id"));
		assertEquals("101", lines.get(1).get("employee_id"));
		assertEquals("Jane", lines.get(1).get("first_name"));
		assertEquals("Smith", lines.get(1).get("last_name"));
		assertEquals("jane.smith@example.com", lines.get(1).get("email"));
		assertEquals("Marketing", lines.get(1).get("department"));
		assertEquals("Marketing Manager", lines.get(1).get("position"));
		assertEquals("90000.0", lines.get(1).get("salary"));
	}

	@Test
	public void testReadLinesNonExistent() {
		List<Map<String, String>> lines = CSVHelper.readLinesAsMap("src/databases/employees.csv", 100, 101);

		// Return empty array if lines not found
		// This is so if the the user specifies a range where some lines do not exist
		// The method will just return the lines that do exist
		assertEquals(0, lines.size());
	}

	@Test
	public void testReadLinesInvalid() {
		List<Map<String, String>> lines = CSVHelper.readLinesAsMap("src/databases/employees.csv", -1, 0);

		// Return empty array if lines not found
		// This is so if the the user specifies a range where some lines do not exist
		// The method will just return the lines that do exist
		assertEquals(0, lines.size());
	}

	// Search Line Tests

	@Test
	public void testSearchLineAsMap() {
		Map<String, String> line = CSVHelper.searchLineAsMap("src/databases/employees.csv", "salary", "75000.0");

		assertNotNull(line);

		assertEquals("1", line.get("id"));
		assertEquals("100", line.get("employee_id"));
		assertEquals("John", line.get("first_name"));
		assertEquals("Doe", line.get("last_name"));
		assertEquals("john.doe@example.com", line.get("email"));
		assertEquals("Engineering", line.get("department"));
		assertEquals("Software Engineer", line.get("position"));
		assertEquals("75000.0", line.get("salary"));
	}

	@Test
	public void testSearchLineNonExistent() {
		Map<String, String> line = CSVHelper.searchLineAsMap("src/databases/employees.csv", "salary", "100000.0");

		assertEquals(null, line);
	}

	@Test
	public void testSearchLineInvalid() {
		Map<String, String> line = CSVHelper.searchLineAsMap("src/databases/employees.csv", "salary", "-1");

		assertEquals(null, line);
	}

	// Search Lines Tests

	@Test
	public void testSearchLinesAsMap() {
		List<Map<String, String>> lines = CSVHelper.searchLinesAsMap("src/databases/employees.csv", "salary", "75000.0",
				2);

		assertNotNull(lines);
		assertEquals(2, lines.size());

		assertEquals("1", lines.get(0).get("id"));
		assertEquals("100", lines.get(0).get("employee_id"));
		assertEquals("John", lines.get(0).get("first_name"));
		assertEquals("Doe", lines.get(0).get("last_name"));
		assertEquals("john.doe@example.com", lines.get(0).get("email"));
		assertEquals("Engineering", lines.get(0).get("department"));
		assertEquals("Software Engineer", lines.get(0).get("position"));
		assertEquals("75000.0", lines.get(0).get("salary"));

		assertEquals("4", lines.get(1).get("id"));
		assertEquals("103", lines.get(1).get("employee_id"));
		assertEquals("Michael", lines.get(1).get("first_name"));
		assertEquals("Brown", lines.get(1).get("last_name"));
		assertEquals("michael.brown@example.com", lines.get(1).get("email"));
		assertEquals("HR", lines.get(1).get("department"));
		assertEquals("HR Manager", lines.get(1).get("position"));
		assertEquals("75000.0", lines.get(1).get("salary"));
	}

	@Test
	public void testSearchLinesNonExistent() {
		List<Map<String, String>> lines = CSVHelper.searchLinesAsMap("src/databases/employees.csv", "salary", "100000.0",
				2);

		// Return empty array if lines not found or just the lines that do exist
		// This is so if the the user specifies a range where some lines do not exist
		// The method will not error out and just return the lines that do exist
		assertEquals(0, lines.size());
	}

	@Test
	public void testSearchLinesInvalid() {
		List<Map<String, String>> lines = CSVHelper.searchLinesAsMap("src/databases/employees.csv", "salary", "-1", 2);

		// Return empty array if lines not found or just the lines that do exist
		// This is so if the the user specifies a range where some of the lines do not
		// exist
		assertEquals(0, lines.size());
	}
}
