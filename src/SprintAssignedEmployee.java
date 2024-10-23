import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class SprintAssignedEmployee {

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
		try {
			return CSVHelper.readBeansFromCsv("src/databases/sprint_assigned_employees.csv",
					SprintAssignedEmployee.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves a list of sprint assigned employees to the CSV file.
	 *
	 * @param sprintAssignedEmployees The list of sprint assigned employees to save.
	 */
	private static void saveSprintAssignedEmployees(List<SprintAssignedEmployee> sprintAssignedEmployees) {
		try {
			CSVHelper.writeBeansToCsv(sprintAssignedEmployees, "src/databases/sprint_assigned_employees.csv",
					SprintAssignedEmployee.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves a single sprint assigned employee to the CSV file.
	 * 
	 * @param sprintAssignedEmployee The sprint assigned employee to save.
	 */
	public static void saveSprintAssignedEmployee(SprintAssignedEmployee sprintAssignedEmployee) {
		List<SprintAssignedEmployee> sprintAssignedEmployees = getSprintAssignedEmployees();
		sprintAssignedEmployees.add(sprintAssignedEmployee);
		saveSprintAssignedEmployees(sprintAssignedEmployees);
	}

	/**
	 * Deletes a single sprint assigned employee from the CSV file.
	 * 
	 * @param id The ID of the sprint assigned employee to delete.
	 */
	public static void deleteSprintAssignedEmployee(int id) {
		List<SprintAssignedEmployee> sprintAssignedEmployees = getSprintAssignedEmployees();
		for (SprintAssignedEmployee sprintAssignedEmployee : sprintAssignedEmployees) {
			if (sprintAssignedEmployee.getId() == id) {
				sprintAssignedEmployees.remove(sprintAssignedEmployee);
				saveSprintAssignedEmployees(sprintAssignedEmployees);
				return;
			}
		}
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
