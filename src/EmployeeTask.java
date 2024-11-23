import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class EmployeeTask implements Identifiable {
	private static final String databasePath = "src/databases/employee_tasks.csv";

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "employee_id")
	private int employeeId;

	@CsvBindByName(column = "task_name")
	private String taskName;

	@CsvBindByName(column = "task_description")
	private String taskDescription;

	@CsvBindByName(column = "task_status")
	private Boolean taskStatus;

	@CsvBindByName(column = "task_start_date")
	private String taskStartDate;

	@CsvBindByName(column = "task_end_date")
	private String taskEndDate;

	// Constructors
	public EmployeeTask() {
	}

	public EmployeeTask(int employeeId, String taskName, String taskDescription, Boolean taskStatus,
			String taskStartDate, String taskEndDate) {
		this.id = findNextId();
		this.employeeId = employeeId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskStatus = taskStatus;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
	}

	// Helper Methods

	/**
	 * Finds the next available ID for an EmployeeTask object.
	 *
	 * @return The next available ID.
	 */
	private int findNextId() {
		List<EmployeeTask> employeeTasks = getEmployeeTasks();
		if (employeeTasks.isEmpty()) {
			return 1;
		} else {
			return employeeTasks.get(employeeTasks.size() - 1).getId() + 1;
		}
	}

	/**
	 * Retrieves a list of all employee tasks from the database.
	 *
	 * @return A list of all employee tasks.
	 */
	public static List<EmployeeTask> getEmployeeTasks() {
		return CSVHelper.get(databasePath, EmployeeTask.class);
	}

	/**
	 * Retrieves an employee task by ID from the database.
	 *
	 * @param employeeId The ID of the employee.
	 * @return A list of all employee tasks for the specified employee.
	 */
	public static EmployeeTask getEmployeeTask(int task_id) {
		return CSVHelper.get(databasePath, EmployeeTask.class, task_id);
	}

	/**
	 * Saves a single employee task to the database.
	 *
	 * @param employeeTask The employee task to save.
	 * @return True if the save was successful, false otherwise.
	 */
	public static void saveEmployeeTask(EmployeeTask employeeTask) {
		CSVHelper.save(employeeTask, databasePath, EmployeeTask.class);
	}

	/**
	 * Updates an existing employee task in the database.
	 *
	 * @param employeeTask The employee task to update.
	 * @return True if the update was successful, false otherwise.
	 */
	public static void updateEmployeeTask(EmployeeTask employeeTask) {
		CSVHelper.update(employeeTask, databasePath, EmployeeTask.class);
	}

	/**
	 * Deletes an existing employee task from the database.
	 *
	 * @param employeeTask The employee task to delete.
	 * @return True if the delete was successful, false otherwise.
	 */
	public static void deleteEmployeeTask(EmployeeTask employeeTask) {
		CSVHelper.delete(employeeTask, databasePath, EmployeeTask.class);
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getName() {
		return taskName;
	}

	public String getDescription() {
		return taskDescription;
	}

	public Boolean getStatus() {
		return taskStatus;
	}

	public String getStartDate() {
		return taskStartDate;
	}

	public String getEndDate() {
		return taskEndDate;
	}

	// Setters
	public void setName(String taskName) {
		this.taskName = taskName;
	}

	public void setDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setStatus(Boolean taskStatus) {
		this.taskStatus = taskStatus;
	}

	public void setStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public void setEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	@Override
	public String toString() {
		return "EmployeeTask{" +
				"id=" + id +
				", employeeId=" + employeeId +
				", taskName='" + taskName + '\'' +
				", taskDescription='" + taskDescription + '\'' +
				", taskStatus='" + taskStatus + '\'' +
				", taskStartDate='" + taskStartDate + '\'' +
				", taskEndDate='" + taskEndDate + '\'' +
				'}';
	}

}
