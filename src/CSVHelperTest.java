import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	public void testReadLineAsStrings() {
		String[] line = CSVHelper.readLineAsStrings("src/databases/employees.csv", 1);

		assertNotNull(line);

		assertEquals("1", line[0]);
		assertEquals("John", line[1]);
		assertEquals("Doe", line[2]);
		assertEquals("john.doe@example.com", line[3]);
		assertEquals("Engineering", line[4]);
		assertEquals("Software Engineer", line[5]);
		assertEquals("75000", line[6]);
	}

	@Test
	public void testReadLineAsMap() {
		Map<String, String> line = CSVHelper.readLineAsMap("src/databases/employees.csv", 1);

		assertNotNull(line);

		assertEquals("1", line.get("employee_id"));
		assertEquals("John", line.get("first_name"));
		assertEquals("Doe", line.get("last_name"));
		assertEquals("john.doe@example.com", line.get("email"));
		assertEquals("Engineering", line.get("department"));
		assertEquals("Software Engineer", line.get("position"));
		assertEquals("75000", line.get("salary"));
	}

	// Read Lines Tests
	@Test
	public void testReadLinesAsStrings() {
		String[][] lines = CSVHelper.readLinesAsStrings("src/databases/employees.csv", 1, 2);

		assertNotNull(lines);
		assertEquals(2, lines.length);

		assertEquals(2, lines.length);
		assertEquals("1", lines[0][0]);
		assertEquals("John", lines[0][1]);
		assertEquals("Doe", lines[0][2]);
		assertEquals("john.doe@example.com", lines[0][3]);
		assertEquals("Engineering", lines[0][4]);
		assertEquals("Software Engineer", lines[0][5]);
		assertEquals("75000", lines[0][6]);

		assertEquals("2", lines[1][0]);
		assertEquals("Jane", lines[1][1]);
		assertEquals("Smith", lines[1][2]);
		assertEquals("jane.smith@example.com", lines[1][3]);
		assertEquals("Marketing", lines[1][4]);
		assertEquals("Marketing Manager", lines[1][5]);
		assertEquals("90000", lines[1][6]);
	}

	@Test
	public void testReadLinesAsMap() {
		Map<String, String>[] lines = CSVHelper.readLinesAsMap("src/databases/employees.csv", 1, 2);

		assertNotNull(lines);
		assertEquals(2, lines.length);

		assertEquals("1", lines[0].get("employee_id"));
		assertEquals("John", lines[0].get("first_name"));
		assertEquals("Doe", lines[0].get("last_name"));
		assertEquals("john.doe@example.com", lines[0].get("email"));
		assertEquals("Engineering", lines[0].get("department"));
		assertEquals("Software Engineer", lines[0].get("position"));
		assertEquals("75000", lines[0].get("salary"));

		assertEquals("2", lines[1].get("employee_id"));
		assertEquals("Jane", lines[1].get("first_name"));
		assertEquals("Smith", lines[1].get("last_name"));
		assertEquals("jane.smith@example.com", lines[1].get("email"));
		assertEquals("Marketing", lines[1].get("department"));
		assertEquals("Marketing Manager", lines[1].get("position"));
		assertEquals("90000", lines[1].get("salary"));
	}

	// Search Line Tests
	@Test
	public void testSearchLineAsStrings() {
		String[] line = CSVHelper.searchLineAsStrings("src/databases/employees.csv", "employee_id", "1");

		assertNotNull(line);

		assertEquals("1", line[0]);
		assertEquals("John", line[1]);
		assertEquals("Doe", line[2]);
		assertEquals("john.doe@example.com", line[3]);
		assertEquals("Engineering", line[4]);
		assertEquals("Software Engineer", line[5]);
		assertEquals("75000", line[6]);
	}

	@Test
	public void testSearchLineAsMap() {
		Map<String, String> line = CSVHelper.searchLineAsMap("src/databases/employees.csv", "salary", "75000");

		assertNotNull(line);

		assertEquals("1", line.get("employee_id"));
		assertEquals("John", line.get("first_name"));
		assertEquals("Doe", line.get("last_name"));
		assertEquals("john.doe@example.com", line.get("email"));
		assertEquals("Engineering", line.get("department"));
		assertEquals("Software Engineer", line.get("position"));
		assertEquals("75000", line.get("salary"));
	}

	// Search Lines Tests
	@Test
	public void testSearchLinesAsStrings() {
		String[][] lines = CSVHelper.searchLinesAsStrings("src/databases/employees.csv", "salary", "75000", 2);

		assertNotNull(lines);
		assertEquals(2, lines.length);

		assertEquals("1", lines[0][0]);
		assertEquals("John", lines[0][1]);
		assertEquals("Doe", lines[0][2]);
		assertEquals("john.doe@example.com", lines[0][3]);
		assertEquals("Engineering", lines[0][4]);
		assertEquals("Software Engineer", lines[0][5]);
		assertEquals("75000", lines[0][6]);

		assertEquals("4", lines[1][0]);
		assertEquals("Michael", lines[1][1]);
		assertEquals("Brown", lines[1][2]);
		assertEquals("michael.brown@example.com", lines[1][3]);
		assertEquals("HR", lines[1][4]);
		assertEquals("HR Manager", lines[1][5]);
		assertEquals("75000", lines[1][6]);
	}

	@Test
	public void testSearchLinesAsMap() {
		Map<String, String>[] lines = CSVHelper.searchLinesAsMap("src/databases/employees.csv", "salary", "75000", 2);

		assertNotNull(lines);
		assertEquals(2, lines.length);

		assertEquals("1", lines[0].get("employee_id"));
		assertEquals("John", lines[0].get("first_name"));
		assertEquals("Doe", lines[0].get("last_name"));
		assertEquals("john.doe@example.com", lines[0].get("email"));
		assertEquals("Engineering", lines[0].get("department"));
		assertEquals("Software Engineer", lines[0].get("position"));
		assertEquals("75000", lines[0].get("salary"));

		assertEquals("4", lines[1].get("employee_id"));
		assertEquals("Michael", lines[1].get("first_name"));
		assertEquals("Brown", lines[1].get("last_name"));
		assertEquals("michael.brown@example.com", lines[1].get("email"));
		assertEquals("HR", lines[1].get("department"));
		assertEquals("HR Manager", lines[1].get("position"));
		assertEquals("75000", lines[1].get("salary"));
	}
}
