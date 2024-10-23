import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class EmployeeDemographic implements Identifiable {
	private static final String databasePath = "src/databases/employee_demographics.csv";

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "employee_id")
	private int employeeId;

	@CsvBindByName(column = "birth_date")
	private String birthDate;

	@CsvBindByName(column = "Gender")
	private String gender;

	@CsvBindByName(column = "ethnicity")
	private String ethnicity;

	@CsvBindByName(column = "employment_status")
	private String employmentStatus;

	// Constructors
	public EmployeeDemographic() {
	}

	public EmployeeDemographic(int employeeId, String birthDate, String gender, String ethnicity,
			String employmentStatus) {
		this.id = findNextId();
		this.employeeId = employeeId;
		this.birthDate = birthDate;
		this.gender = gender;
		this.ethnicity = ethnicity;
		this.employmentStatus = employmentStatus;
	}

	// Helper Methods
	private static int findNextId() {
		List<EmployeeDemographic> demographics = getEmployeeDemographics();
		int nextId = 1;
		for (EmployeeDemographic demographic : demographics) {
			if (demographic.getId() >= nextId) {
				nextId = demographic.getId() + 1;
			}
		}
		return nextId;
	}

	// CRUD Methods

	public static List<EmployeeDemographic> getEmployeeDemographics() {
		return CSVHelper.get(databasePath, EmployeeDemographic.class);
	}

	public static EmployeeDemographic getEmployeeDemographic(int id) {
		return CSVHelper.get(databasePath, EmployeeDemographic.class, id);
	}

	public static void saveEmployeeDemographic(EmployeeDemographic demographic) {
		CSVHelper.save(demographic, databasePath, EmployeeDemographic.class);
	}

	public static void updateEmployeeDemographic(EmployeeDemographic demographic) {
		CSVHelper.update(demographic, databasePath, EmployeeDemographic.class);
	}

	public static void deleteEmployeeDemographic(EmployeeDemographic demographic) {
		CSVHelper.delete(demographic, databasePath, EmployeeDemographic.class);
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getGender() {
		return gender;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	// Setters
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	@Override
	public String toString() {
		return "EmployeeDemographic{" +
				"id=" + id +
				", employeeId=" + employeeId +
				", birthDate='" + birthDate + '\'' +
				", gender='" + gender + '\'' +
				", ethnicity='" + ethnicity + '\'' +
				", employmentStatus='" + employmentStatus + '\'' +
				'}';
	}

}
