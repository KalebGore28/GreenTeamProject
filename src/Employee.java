import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Employee {
	private final int id;
	private String firstName;
	private String lastName;
	private String email;
	private String department;
	private String position;
	private double salary;

	public Employee(int id, String firstName, String lastName, String email, String department, String position,
			double salary) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.position = position;
		this.salary = salary;
	}

	/**
	 * Retrieves an employee record from a CSV file based on the provided ID.
	 *
	 * @param id The ID of the employee to retrieve. Starts from 1.
	 * @return An {@code Optional<Employee>} containing the employee if found, or an
	 *         empty {@code Optional} if not found.
	 */
	public static Optional<Employee> getEmployee(int id) {

		// Search for the employee by ID
		Map<String, String> data = CSVHelper.searchLineAsMap("src/databases/employees.csv", "employee_id",
				String.valueOf(id));

		// Return empty Optional if employee not found
		if (data == null) {
			return Optional.empty();
		}

		// Create and return an Optional containing the employee
		Employee employee = new Employee(
				Integer.parseInt(data.get("employee_id")),
				data.get("first_name"),
				data.get("last_name"),
				data.get("email"),
				data.get("department"),
				data.get("position"),
				Double.parseDouble(data.get("salary")));
		return Optional.of(employee);
	}

	/**
	 * Retrieves a range of employee records from a CSV file based on the provided
	 * row range.
	 *
	 * @param startRow The starting row of the range to retrieve. Starts from 1.
	 * @param endRow   The ending row of the range to retrieve. Starts from 1.
	 * @return An {@code Optional<Employee[]>} containing the employees if found, or
	 *         an empty {@code Optional} if not found.
	 */
	public static Optional<Employee[]> getEmployees(int startRow, int endRow) {

		// Search for the employees by row range
		List<Map<String, String>> data = CSVHelper.readLinesAsMap("src/databases/employees.csv", startRow, endRow);

		// Return empty Optional if employees not found
		if (data.size() == 0) {
			return Optional.empty();
		}

		// Create and return an Optional containing the employees
		Employee[] employees = new Employee[data.size()];
		for (int i = 0; i < data.size(); i++) {
			Map<String, String> employeeData = data.get(i);
			employees[i] = new Employee(
					Integer.parseInt(employeeData.get("employee_id")),
					employeeData.get("first_name"),
					employeeData.get("last_name"),
					employeeData.get("email"),
					employeeData.get("department"),
					employeeData.get("position"),
					Double.parseDouble(employeeData.get("salary")));
		}

		return Optional.of(employees);
	}

	public static Optional<Employee[]> searchEmployees(String column, String value, int limit) {

		// Search for the employees by column and value
		List<Map<String, String>> data = CSVHelper.searchLinesAsMap("src/databases/employees.csv", column, value, limit);

		// Return empty Optional if employees not found
		if (data.size() == 0) {
			return Optional.empty();
		}

		// Create and return an Optional containing the employees
		Employee[] employees = new Employee[data.size()];
		for (int i = 0; i < data.size(); i++) {
			Map<String, String> employeeData = data.get(i);
			employees[i] = new Employee(
					Integer.parseInt(employeeData.get("employee_id")),
					employeeData.get("first_name"),
					employeeData.get("last_name"),
					employeeData.get("email"),
					employeeData.get("department"),
					employeeData.get("position"),
					Double.parseDouble(employeeData.get("salary")));
		}

		return Optional.of(employees);
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}

	public String getPosition() {
		return position;
	}

	public double getSalary() {
		return salary;
	}

	// Setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", department=" + department + ", position=" + position + ", salary=" + salary + "]";
	}

}
