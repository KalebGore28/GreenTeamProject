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

    /**
     * Default constructor.
     */
    public EmployeeSkill() {
    }

    /**
     * Parameterized constructor to initialize an EmployeeSkill object.
     *
     * @param employeeId        The ID of the employee.
     * @param skillName         The name of the skill.
     * @param proficiencyLevel  The proficiency level of the skill.
     * @param yearsOfExperience The number of years of experience with the skill.
     * @param lastUsedDate      The date when the skill was last used.
     */
    public EmployeeSkill(int employeeId, String skillName, String proficiencyLevel, int yearsOfExperience,
                         String lastUsedDate) {
        this.employeeId = employeeId;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
        this.lastUsedDate = lastUsedDate;
    }

    // Static Get Methods

    /**
     * Retrieves a list of all employee skills from the CSV file.
     *
     * @return A list of EmployeeSkill objects, or null if an error occurs.
     */
    public static List<EmployeeSkill> getEmployeeSkills() {
        try {
            return CSVHelper.readBeansFromCsv("src/databases/employee_skills.csv", EmployeeSkill.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a specific employee skill by the employee ID from the CSV file.
     *
     * @param employee_id The ID of the employee whose skill is to be retrieved.
     * @return The EmployeeSkill object with the specified employee ID, or null if not found or an error occurs.
     */
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

    /**
     * Helper method to set the list of employee skills to the CSV file.
     * This method is not meant to be called directly.
     *
     * @param employeeSkills The list of EmployeeSkill objects to save.
     */
    private static void setEmployeeSkills(List<EmployeeSkill> employeeSkills) {
        try {
            CSVHelper.writeBeansToCsv(employeeSkills, "src/databases/employee_skills.csv", EmployeeSkill.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a single employee skill in the CSV file.
     *
     * @param employeeSkill The EmployeeSkill object to set.
     */
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

    /**
     * Updates an existing employee skill in the CSV file.
     *
     * @param employeeSkill The EmployeeSkill object with updated information.
     */
    public static void updateSkill(EmployeeSkill employeeSkill) {
        List<EmployeeSkill> employeeSkills = getEmployeeSkills();
        for (int i = 0; i < employeeSkills.size(); i++) {
            if (employeeSkills.get(i).getId() == employeeSkill.getId()) {
                employeeSkills.set(i, employeeSkill);
                break;
            }
        }
        setEmployeeSkills(employeeSkills);
    }

    /**
     * Deletes an employee skill from the CSV file by its ID.
     *
     * @param id The ID of the employee skill to delete.
     */
    public static void deleteSkill(int id) {
        List<EmployeeSkill> employeeSkills = getEmployeeSkills();
        for (int i = 0; i < employeeSkills.size(); i++) {
            if (employeeSkills.get(i).getId() == id) {
                employeeSkills.remove(i);
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