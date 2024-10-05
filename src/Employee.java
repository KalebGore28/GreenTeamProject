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
		String[] data = CSVHelper.searchLineAsStrings("src/databases/employees.csv", "employee_id", String.valueOf(id));
		if (data == null || data.length == 0) {
			return Optional.empty(); // Return empty Optional if employee not found
		}
		Employee employee = new Employee(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5],
				Double.parseDouble(data[6]));
		return Optional.of(employee);
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

	public static void main(String[] args) {
		Optional<Employee> employee = Employee.getEmployee(6);
		if (employee.isPresent()) {
			System.out.println(employee.get());
		} else {
			System.out.println("Employee not found.");
		}
	}
}
