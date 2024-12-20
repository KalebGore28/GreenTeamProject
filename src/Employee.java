import com.opencsv.bean.CsvBindByName;
import java.util.List;
import java.util.ArrayList;

public class Employee implements User, Identifiable {
	private static final String databasePath = "src/databases/employees.csv";

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

	// CRUD Methods

	/**
	 * Retrieves a list of all employees from the CSV file.
	 *
	 * @return A list of Employee objects, or null if an error occurs.
	 */
	public static List<Employee> getEmployees() {
		return CSVHelper.get(databasePath, Employee.class);
	}

	/**
	 * Retrieves a single employee by their ID.
	 *
	 * @param employee_id The ID of the employee to retrieve.
	 * @return The Employee object, or null if the employee is not found.
	 */
	public static Employee getEmployee(int employee_id) {
		return CSVHelper.get(databasePath, Employee.class, employee_id);
	}

	/**
	 * Saves a new employee to the CSV file.
	 *
	 * @param employee The Employee object to save.
	 */
	public static void saveEmployee(Employee employee) {
		CSVHelper.save(employee, databasePath, Employee.class);
	}

	/**
	 * Updates an existing employee in the CSV file.
	 *
	 * @param employee The Employee object with updated information.
	 */
	public static void updateEmployee(Employee employee) {
		CSVHelper.update(employee, databasePath, Employee.class);
	}

	/**
	 * Deletes an employee from the CSV file by their ID.
	 *
	 * @param employee_id The ID of the employee to delete.
	 */
	public static void deleteEmployee(Employee employee) {
		CSVHelper.delete(employee, databasePath, Employee.class);
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
	 * Saves a new employee history to the CSV file.
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
	public void deleteHistory(EmployeeHistory employeeHistory) {
		EmployeeHistory.deleteHistory(employeeHistory);
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
	public void newHistory(String company, String department, String position, String startDate, String endDate,
			double salary, String reasonForLeaving) {
		saveHistory(new EmployeeHistory(this.id, company, department, position, startDate, endDate, salary,
				reasonForLeaving));
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
	public void deleteSkill(EmployeeSkill employeeSkill) {
		EmployeeSkill.deleteSkill(employeeSkill);
	}

	/**
	 * Creates a new employee skill and saves it to the CSV file.
	 *
	 * @param skillName         The name of the skill.
	 * @param proficiencyLevel  The proficiency level of the skill.
	 * @param yearsOfExperience The number of years of experience with the skill.
	 * @param lastUsedDate      The date when the skill was last used.
	 */
	public void newSkill(String skillName, String proficiencyLevel, int yearsOfExperience,
			String lastUsedDate) {
		saveSkill(new EmployeeSkill(this.id, skillName, proficiencyLevel, yearsOfExperience, lastUsedDate));
	}

	// Get, Save, Update, Delete, New Tasks Methods

	/**
	 * Retrieves a list of all employee tasks from the CSV file.
	 *
	 * @return A list of EmployeeTask objects, or null if an error occurs.
	 */
	public List<EmployeeTask> getTasks() {
		try {
			List<EmployeeTask> allEmployeeTasks = EmployeeTask.getEmployeeTasks();
			List<EmployeeTask> foundEmployeeTasks = new ArrayList<EmployeeTask>();
			for (EmployeeTask employeeTask : allEmployeeTasks) {
				if (employeeTask.getEmployeeId() == this.id) {
					foundEmployeeTasks.add(employeeTask);
				}
			}
			return foundEmployeeTasks;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves a single employee task to the CSV file.
	 *
	 * @param employeeTask The EmployeeTask object to save.
	 */
	public void saveTask(EmployeeTask employeeTask) {
		EmployeeTask.saveEmployeeTask(employeeTask);
	}

	/**
	 * Updates an existing employee task in the CSV file.
	 *
	 * @param employeeTask The EmployeeTask object with updated information.
	 */
	public void updateTask(EmployeeTask employeeTask) {
		EmployeeTask.updateEmployeeTask(employeeTask);
	}

	/**
	 * Deletes an employee task from the CSV file by their ID.
	 *
	 * @param employeeTask_id The ID of the employee task to delete.
	 */
	public void deleteTask(EmployeeTask employeeTask) {
		EmployeeTask.deleteEmployeeTask(employeeTask);
	}

	/**
	 * Creates a new employee task and saves it to the CSV file.
	 *
	 * @param taskName        The name of the task.
	 * @param taskDescription The description of the task.
	 * @param taskStatus      The status of the task.
	 * @param taskStartDate   The start date of the task.
	 * @param taskEndDate     The end date of the task.
	 */
	public void newTask(String taskName, String taskDescription, Boolean taskStatus, String taskStartDate,
			String taskEndDate) {
		EmployeeTask.saveEmployeeTask(
				new EmployeeTask(this.id, taskName, taskDescription, taskStatus, taskStartDate, taskEndDate));
	}

	// Get, Save, Update, Delete, New Employee Demographics Methods

	/**
	 * Retrieves a list of all employee demographics from the CSV file.
	 *
	 * @return A list of EmployeeDemographic objects, or null if an error occurs.
	 */
	public EmployeeDemographic getDemographic() {
		return EmployeeDemographic.getEmployeeDemographic(this.id);
	}

	/**
	 * Saves a single employee demographic to the CSV file.
	 *
	 * @param employeeDemographic The EmployeeDemographic object to save.
	 */
	public void saveDemographic(EmployeeDemographic employeeDemographic) {
		EmployeeDemographic.saveEmployeeDemographic(employeeDemographic);
	}

	/**
	 * Updates an existing employee demographic in the CSV file.
	 *
	 * @param employeeDemographic The EmployeeDemographic object with updated
	 *                            information.
	 */
	public void updateDemographic(EmployeeDemographic employeeDemographic) {
		EmployeeDemographic.updateEmployeeDemographic(employeeDemographic);
	}

	/**
	 * Deletes an employee demographic from the CSV file by their ID.
	 *
	 * @param employeeDemographic_id The ID of the employee demographic to delete.
	 */
	public void deleteDemographic(EmployeeDemographic employeeDemographic) {
		EmployeeDemographic.deleteEmployeeDemographic(employeeDemographic);
	}

	/**
	 * Creates a new employee demographic and saves it to the CSV file.
	 *
	 * @param employeeId       The ID of the employee.
	 * @param birthDate        The birth date of the employee.
	 * @param gender           The gender of the employee.
	 * @param ethnicity        The ethnicity of the employee.
	 * @param employmentStatus The employment status of the employee.
	 */
	public void newDemographic(String birthDate, String gender, String ethnicity, String employmentStatus) {
		EmployeeDemographic.saveEmployeeDemographic(
				new EmployeeDemographic(this.id, birthDate, gender, ethnicity, employmentStatus));
	}

	// Getters

	/**
	 * Retrieves a list of all sprint evaluations from the CSV file.
	 *
	 * @return A list of SprintEvaluation objects, or null if an error occurs.
	 */
	public List<SprintEvaluation> getEvaluations() {
		try {
			List<SprintEvaluation> allSprintEvaluations = SprintEvaluation.getSprintEvaluations();
			List<SprintEvaluation> foundSprintEvaluations = new ArrayList<SprintEvaluation>();
			for (SprintEvaluation sprintEvaluation : allSprintEvaluations) {
				if (sprintEvaluation.getEmployeeId() == this.id) {
					foundSprintEvaluations.add(sprintEvaluation);
				}
			}
			return foundSprintEvaluations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
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

	public String getRole() {
		return "Employee";
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