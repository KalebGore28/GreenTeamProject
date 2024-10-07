import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class EmployeeHistory {

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "employee_id")
	private int employeeId;

	@CsvBindByName(column = "department")
	private String department;

	@CsvBindByName(column = "position")
	private String position;

	@CsvBindByName(column = "start_date")
	private String startDate;

	@CsvBindByName(column = "end_date")
	private String endDate;

	@CsvBindByName(column = "salary")
	private double salary;

	@CsvBindByName(column = "reason_for_leaving")
	private String reasonForLeaving;

	// Constructors
	public EmployeeHistory() {
	}

	public EmployeeHistory(int employeeId, String department, String position, String startDate, String endDate,
			double salary, String reasonForLeaving) {
		this.employeeId = employeeId;
		this.department = department;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.salary = salary;
		this.reasonForLeaving = reasonForLeaving;
	}

	// Static Get Methods
	public static List<EmployeeHistory> getEmployeeHistories() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/employee_histories.csv", EmployeeHistory.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static EmployeeHistory getEmployeeHistory(int employee_id) {
		try {
			List<EmployeeHistory> employeeHistories = CSVHelper.readBeansFromCsv("src/databases/employee_histories.csv",
					EmployeeHistory.class);
			for (EmployeeHistory employeeHistory : employeeHistories) {
				if (employeeHistory.getEmployeeId() == employee_id) {
					return employeeHistory;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// Static Save Methods
	public static void saveEmployeeHistories(List<EmployeeHistory> employeeHistories) {
		try {
			CSVHelper.writeBeansToCsv(employeeHistories, "src/databases/employee_histories.csv", EmployeeHistory.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveEmployeeHistory(EmployeeHistory employeeHistory) {
		List<EmployeeHistory> employeeHistories = getEmployeeHistories();
		employeeHistories.add(employeeHistory);
		saveEmployeeHistories(employeeHistories);
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getDepartment() {
		return department;
	}

	public String getPosition() {
		return position;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public double getSalary() {
		return salary;
	}

	public String getReasonForLeaving() {
		return reasonForLeaving;
	}

	// Setters
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

}
