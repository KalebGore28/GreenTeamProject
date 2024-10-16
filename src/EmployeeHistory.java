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

    /**
     * Default constructor.
     */
    public EmployeeHistory() {
    }

    /**
     * Parameterized constructor to initialize an EmployeeHistory object.
     *
     * @param employeeId       The ID of the employee.
     * @param department       The department where the employee worked.
     * @param position         The position or job title held by the employee.
     * @param startDate        The start date of the position.
     * @param endDate          The end date of the position.
     * @param salary           The salary of the employee during this position.
     * @param reasonForLeaving The reason why the employee left the position.
     */
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

    // Static Get, Save, Update, Delete Methods

    /**
     * Retrieves a list of all employee histories from the CSV file.
     *
     * @return A list of EmployeeHistory objects, or null if an error occurs.
     */
    public static List<EmployeeHistory> getHistories() {
        try {
            return CSVHelper.readBeansFromCsv("src/databases/employee_histories.csv", EmployeeHistory.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method to save a list of EmployeeHistory objects to the CSV file.
     * This method is not meant to be called directly.
     *
     * @param employeeHistories The list of EmployeeHistory objects to save.
     */
    private static void saveHistories(List<EmployeeHistory> employeeHistories) {
        try {
            CSVHelper.writeBeansToCsv(employeeHistories, "src/databases/employee_histories.csv", EmployeeHistory.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a single employee history to the CSV file.
     *
     * @param employeeHistory The EmployeeHistory object to save.
     */
    public static void saveHistory(EmployeeHistory employeeHistory) {
        List<EmployeeHistory> employeeHistories = getHistories();
        employeeHistories.add(employeeHistory);
        saveHistories(employeeHistories);
    }

    /**
     * Updates an existing employee history in the CSV file.
     *
     * @param employeeHistory The EmployeeHistory object with updated information.
     */
    public static void updateHistory(EmployeeHistory employeeHistory) {
        List<EmployeeHistory> employeeHistories = getHistories();
        for (int i = 0; i < employeeHistories.size(); i++) {
            if (employeeHistories.get(i).getId() == employeeHistory.getId()) {
                employeeHistories.set(i, employeeHistory);
                break;
            }
        }
        saveHistories(employeeHistories);
    }

    /**
     * Deletes an employee history from the CSV file by its ID.
     *
     * @param id The ID of the employee history to delete.
     */
    public static void deleteHistory(int id) {
        List<EmployeeHistory> employeeHistories = getHistories();
        for (int i = 0; i < employeeHistories.size(); i++) {
            if (employeeHistories.get(i).getId() == id) {
                employeeHistories.remove(i);
                break;
            }
        }
        saveHistories(employeeHistories);
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