import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class EmployeeHistory implements Identifiable {
    private static final String databasePath = "src/databases/employee_histories.csv";

    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "employee_id")
    private int employeeId;

    @CsvBindByName(column = "company")
    private String company;

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
    public EmployeeHistory(int employeeId, String company, String department, String position, String startDate,
            String endDate, double salary, String reasonForLeaving) {
        this.id = findNextId();
        this.employeeId = employeeId;
        this.company = company;
        this.department = department;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.reasonForLeaving = reasonForLeaving;
    }

    // Helper Methods

    /**
     * Finds the next available ID for a new employee history.
     *
     * @return The next available ID.
     */
    private static int findNextId() {
        List<EmployeeHistory> employeeHistories = getHistories();
        if (employeeHistories == null || employeeHistories.isEmpty()) {
            return 1;
        }
        return employeeHistories.get(employeeHistories.size() - 1).getId() + 1;
    }

    // Static Get, Save, Update, Delete Methods

    /**
     * Retrieves a list of all employee histories from the CSV file.
     *
     * @return A list of EmployeeHistory objects, or null if an error occurs.
     */
    public static List<EmployeeHistory> getHistories() {
        return CSVHelper.get(databasePath, EmployeeHistory.class);
    }

    /**
     * Saves a single employee history to the CSV file.
     *
     * @param employeeHistory The EmployeeHistory object to save.
     */
    public static void saveHistory(EmployeeHistory employeeHistory) {
        CSVHelper.save(employeeHistory, databasePath, EmployeeHistory.class);
    }

    /**
     * Updates an existing employee history in the CSV file.
     *
     * @param employeeHistory The EmployeeHistory object with updated information.
     */
    public static void updateHistory(EmployeeHistory employeeHistory) {
        CSVHelper.update(employeeHistory, databasePath, EmployeeHistory.class);
    }

    /**
     * Deletes an employee history from the CSV file by its ID.
     *
     * @param id The ID of the employee history to delete.
     */
    public static void deleteHistory(EmployeeHistory employeeHistory) {
        CSVHelper.delete(employeeHistory, databasePath, EmployeeHistory.class);
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getCompany() {
        return company;
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

    public void setCompany(String company) {
        this.company = company;
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

    @Override
    public String toString() {
        return "EmployeeHistory{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", salary=" + salary +
                ", reasonForLeaving='" + reasonForLeaving + '\'' +
                '}';
    }
}