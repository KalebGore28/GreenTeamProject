import com.opencsv.bean.CsvBindByName;
import java.util.List;
import java.util.ArrayList;

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
		this.id = findNextId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.position = position;
		this.salary = salary;
	}

	// Helper Methods

	/**
	 * Finds the next available ID for a new employee.
	 *
	 * @return The next available ID.
	 */
	private static int findNextId() {
		List<Employee> employees = getEmployees();
		int nextId = 1;
		for (Employee employee : employees) {
			if (employee.getId() >= nextId) {
				nextId = employee.getId() + 1;
			}
		}
		return nextId;
	}

	// Static Get, Save, Update, Delete Employee Methods

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
	 * Retrieves a single employee from the CSV file by their ID.
	 *
	 * @param employee_id The ID of the employee to retrieve.
	 * @return The Employee object, or null if the employee is not found.
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

	/**
	 * Saves a list of employees to the CSV file.
	 * Not meant to be called directly.
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
	 *
	 * @param employee The Employee object to save.
	 */
	public static void saveEmployee(Employee employee) {
		List<Employee> employees = getEmployees();
		employees.add(employee);
		saveEmployees(employees);
	}

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

	// Get, Save, Update, Delete, New History Methods

	/**
	 * Retrieves a list of all employee histories from the CSV file.
	 *
	 * @return A list of EmployeeHistory objects, or null if an error occurs.
	 */
	public List<EmployeeHistory> getHistories() {
		try {
			List<EmployeeHistory> allEmployeeHistory = EmployeeHistory.getHistories();
			List<EmployeeHistory> foundEmployeeHistory = new ArrayList<EmployeeHistory>();
			for (EmployeeHistory employeeHistory : allEmployeeHistory) {
				if (employeeHistory.getEmployeeId() == this.id) {
					foundEmployeeHistory.add(employeeHistory);
				}
			}
			return foundEmployeeHistory;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves a single employee history to the CSV file.
	 *
	 * @param employeeHistory The EmployeeHistory object to save.
	 */
	public void saveHistory(EmployeeHistory employeeHistory) {
		EmployeeHistory.saveHistory(employeeHistory);
	}

	/**
	 * Updates an existing employee history in the CSV file.
	 *
	 * @param employeeHistory The EmployeeHistory object with updated information.
	 */
	public void updateHistory(EmployeeHistory employeeHistory) {
		EmployeeHistory.updateHistory(employeeHistory);
	}

	/**
	 * Deletes an employee history from the CSV file by their ID.
	 *
	 * @param employeeHistory_id The ID of the employee history to delete.
	 */
	public void deleteHistory(int history_id) {
		EmployeeHistory.deleteHistory(history_id);
	}

	/**
	 * Creates a new employee history and saves it to the CSV file.
	 *
	 * @param employeeId       The ID of the employee.
	 * @param department       The department where the employee worked.
	 * @param position         The position or job title of the employee.
	 * @param startDate        The start date of the employment.
	 * @param endDate          The end date of the employment.
	 * @param salary           The salary of the employee.
	 * @param reasonForLeaving The reason for leaving the position.
	 */
	public EmployeeHistory newHistory(int employeeId, String department, String position, String startDate,
			String endDate,
			double salary, String reasonForLeaving) {
		return new EmployeeHistory(employeeId, department, position, startDate, endDate, salary, reasonForLeaving);
	}

	// Get, Save, Update, Delete, New Skill Methods

	/**
	 * Retrieves a list of all employee skills from the CSV file.
	 *
	 * @return A list of EmployeeSkill objects, or null if an error occurs.
	 */
	public List<EmployeeSkill> getSkills() {
		try {
			List<EmployeeSkill> allEmployeeSkills = EmployeeSkill.getSkills();
			List<EmployeeSkill> foundEmployeeSkills = new ArrayList<EmployeeSkill>();
			for (EmployeeSkill employeeSkill : allEmployeeSkills) {
				if (employeeSkill.getEmployeeId() == this.id) {
					foundEmployeeSkills.add(employeeSkill);
				}
			}
			return foundEmployeeSkills;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves a single employee skill to the CSV file.
	 *
	 * @param employeeSkill The EmployeeSkill object to save.
	 */
	public void saveSkill(EmployeeSkill employeeSkill) {
		EmployeeSkill.saveSkill(employeeSkill);
	}

	/**
	 * Updates an existing employee skill in the CSV file.
	 *
	 * @param employeeSkill The EmployeeSkill object with updated information.
	 */
	public void updateSkill(EmployeeSkill employeeSkill) {
		EmployeeSkill.updateSkill(employeeSkill);
	}

	/**
	 * Deletes an employee skill from the CSV file by their ID.
	 *
	 * @param employeeSkill_id The ID of the employee skill to delete.
	 */
	public void deleteSkill(int skill_id) {
		EmployeeSkill.deleteSkill(skill_id);
	}

	/**
	 * Creates a new employee skill and saves it to the CSV file.
	 *
	 * @param skillName         The name of the skill.
	 * @param proficiencyLevel  The proficiency level of the skill.
	 * @param yearsOfExperience The number of years of experience with the skill.
	 * @param lastUsedDate      The date when the skill was last used.
	 */
	public EmployeeSkill newSkill(String skillName, String proficiencyLevel, int yearsOfExperience,
			String lastUsedDate) {
		return new EmployeeSkill(this.id, skillName, proficiencyLevel, yearsOfExperience, lastUsedDate);
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

	public static void main(String[] args) {
		// Get one employee
		Employee employee = Employee.getEmployee(8);
		for (EmployeeHistory history : employee.getHistories()) {
			System.out.println(history);
		}
	}
}