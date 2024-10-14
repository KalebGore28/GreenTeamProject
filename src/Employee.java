import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class Employee {

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "first_name")
	private String firstName;

	@CsvBindByName(column = "last_name")
	private String lastName;

	@CsvBindByName(column = "email")
	private String email;

	@CsvBindByName(column = "department")
	private String department;

	@CsvBindByName(column = "position")
	private String position;

	@CsvBindByName(column = "salary")
	private double salary;

	// Constructors

	/**
	 * Default constructor.
	 */
	public Employee() {
	}

	/**
	 * Parameterized constructor to initialize an Employee object.
	 *
	 * @param firstName  The first name of the employee.
	 * @param lastName   The last name of the employee.
	 * @param email      The email address of the employee.
	 * @param department The department where the employee works.
	 * @param position   The position or job title of the employee.
	 * @param salary     The salary of the employee.
	 */
	public Employee(String firstName, String lastName, String email, String department,
			String position, double salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.position = position;
		this.salary = salary;
	}

	// Static Get Methods

	/**
	 * Retrieves a list of all employees from the CSV file.
	 *
	 * @return A list of Employee objects, or null if an error occurs.
	 */
	public static List<Employee> getEmployees() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/employees.csv", Employee.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves a specific employee by their ID from the CSV file.
	 *
	 * @param employee_id The ID of the employee to retrieve.
	 * @return The Employee object with the specified ID, or null if not found or an
	 *         error occurs.
	 */
	public static Employee getEmployee(int employee_id) {
		try {
			List<Employee> employees = CSVHelper.readBeansFromCsv("src/databases/employees.csv", Employee.class);
			for (Employee employee : employees) {
				if (employee.getId() == employee_id) {
					return employee;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Static Save Methods

	/**
	 * Helper method to save the list of employees to the CSV file.
	 * This method is not meant to be called directly.
	 *
	 * @param employees The list of Employee objects to save.
	 */
	private static void saveEmployees(List<Employee> employees) {
		try {
			CSVHelper.writeBeansToCsv(employees, "src/databases/employees.csv", Employee.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves a single employee to the CSV file.
	 * If the employee does not have an ID, it assigns the next available ID.
	 *
	 * @param employee The Employee object to save.
	 */
	public static void saveEmployee(Employee employee) {
		List<Employee> employees = getEmployees();

		// Figure out the next available ID
		int nextId = 1;
		for (Employee emp : employees) {
			if (emp.getId() >= nextId) {
				nextId = emp.getId() + 1;
			}
		}

		// Set the ID and add the employee to the list
		employee.id = nextId;
		employees.add(employee);

		// Save the updated list of employees
		saveEmployees(employees);
	}

	// Static Update Method

	/**
	 * Updates an existing employee in the CSV file.
	 *
	 * @param employee The Employee object with updated information.
	 */
	public static void updateEmployee(Employee employee) {
		List<Employee> employees = getEmployees();

		// Find the employee in the list
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId() == employee.getId()) {
				employees.set(i, employee);
				break;
			}
		}

		// Save the updated list of employees
		saveEmployees(employees);
	}

	// Static Delete Method

	/**
	 * Deletes an employee from the CSV file by their ID.
	 *
	 * @param employee_id The ID of the employee to delete.
	 */
	public static void deleteEmployee(int employee_id) {
		List<Employee> employees = getEmployees();

		// Find the employee in the list
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId() == employee_id) {
				employees.remove(i);
				break;
			}
		}

		// Save the updated list of employees
		saveEmployees(employees);
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
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", department=" + department + ", position=" + position + ", salary=" + salary + "]";
	}
}