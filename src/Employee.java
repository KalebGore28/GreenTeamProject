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
	public Employee() {
	}

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
	public static List<Employee> getEmployees() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/employees.csv", Employee.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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
	private static void saveEmployees(List<Employee> employees) {
		try {
			CSVHelper.writeBeansToCsv(employees, "src/databases/employees.csv", Employee.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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