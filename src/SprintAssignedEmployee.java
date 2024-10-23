import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class SprintAssignedEmployee {
	private static final String databasePath = "src/databases/sprint_assigned_employees.csv";

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "sprint_id")
	private int sprintId;

	@CsvBindByName(column = "employee_id")
	private int employeeId;

	// Constructors
	public SprintAssignedEmployee() {
	}

	/**
	 * Parameterized constructor to initialize a SprintAssignedEmployee object.
	 *
	 * @param sprintId   The ID of the sprint.
	 * @param employeeId The ID of the employee.
	 */
	public SprintAssignedEmployee(int sprintId, int employeeId) {
		this.id = findNextId();
		this.sprintId = sprintId;
		this.employeeId = employeeId;
	}

	// Helper Methods

	/**
	 * Finds the next available ID for a new sprint assigned employee.
	 *
	 * @return The next available ID.
	 */
	private static int findNextId() {
		List<SprintAssignedEmployee> sprintAssignedEmployees = getSprintAssignedEmployees();
		int nextId = 1;
		for (SprintAssignedEmployee sprintAssignedEmployee : sprintAssignedEmployees) {
			if (sprintAssignedEmployee.getId() >= nextId) {
				nextId = sprintAssignedEmployee.getId() + 1;
			}
		}
		return nextId;
	}

	// Static Get, Save, Update, Delete Methods
	/**
	 * Gets a list of all sprint assigned employees.
	 *
	 * @return A list of all sprint assigned employees.
	 */
	public static List<SprintAssignedEmployee> getSprintAssignedEmployees() {
		return CSVHelper.get(databasePath, SprintAssignedEmployee.class);
	}

	/**
	 * Saves a single sprint assigned employee to the CSV file.
	 * 
	 * @param sprintAssignedEmployee The sprint assigned employee to save.
	 */
	public static void saveSprintAssignedEmployee(SprintAssignedEmployee sprintAssignedEmployee) {
		CSVHelper.save(sprintAssignedEmployee, databasePath, SprintAssignedEmployee.class);
	}

	/**
	 * Deletes a single sprint assigned employee from the CSV file.
	 * 
	 * @param id The ID of the sprint assigned employee to delete.
	 */
	public static void deleteSprintAssignedEmployee(SprintAssignedEmployee sprintAssignedEmployee) {
		CSVHelper.delete(sprintAssignedEmployee, databasePath, SprintAssignedEmployee.class);
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getSprintId() {
		return sprintId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

}
