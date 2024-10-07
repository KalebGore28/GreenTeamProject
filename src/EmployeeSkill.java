import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class EmployeeSkill {

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "employee_id")
	private int employeeId;

	@CsvBindByName(column = "skill_name")
	private String skillName;

	@CsvBindByName(column = "proficiency_level")
	private String proficiencyLevel;

	@CsvBindByName(column = "years_of_experience")
	private int yearsOfExperience;

	@CsvBindByName(column = "last_used_date")
	private String lastUsedDate;

	// Constructors
	public EmployeeSkill() {
	}

	public EmployeeSkill(int employeeId, String skillName, String proficiencyLevel, int yearsOfExperience,
			String lastUsedDate) {
		this.employeeId = employeeId;
		this.skillName = skillName;
		this.proficiencyLevel = proficiencyLevel;
		this.yearsOfExperience = yearsOfExperience;
		this.lastUsedDate = lastUsedDate;
	}

	// Static Get Methods
	public static List<EmployeeSkill> getEmployeeSkills() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/employee_skills.csv", EmployeeSkill.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static EmployeeSkill getEmployeeSkill(int employee_id) {
		try {
			List<EmployeeSkill> employeeSkills = CSVHelper.readBeansFromCsv("src/databases/employee_skills.csv",
					EmployeeSkill.class);
			for (EmployeeSkill employeeSkill : employeeSkills) {
				if (employeeSkill.getEmployeeId() == employee_id) {
					return employeeSkill;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Static Set Methods
	public static void setEmployeeSkills(List<EmployeeSkill> employeeSkills) {
		try {
			CSVHelper.writeBeansToCsv(employeeSkills, "src/databases/employee_skills.csv", EmployeeSkill.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setEmployeeSkill(EmployeeSkill employeeSkill) {
		List<EmployeeSkill> employeeSkills = getEmployeeSkills();
		for (int i = 0; i < employeeSkills.size(); i++) {
			if (employeeSkills.get(i).getEmployeeId() == employeeSkill.getEmployeeId()) {
				employeeSkills.set(i, employeeSkill);
				break;
			}
		}
		setEmployeeSkills(employeeSkills);
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getSkillName() {
		return skillName;
	}

	public String getProficiencyLevel() {
		return proficiencyLevel;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public String getLastUsedDate() {
		return lastUsedDate;
	}

	// Setters
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public void setProficiencyLevel(String proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public void setLastUsedDate(String lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}
}
