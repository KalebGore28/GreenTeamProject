import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class EmployeeSkill implements Identifiable {
    private static final String databasePath = "src/databases/employee_skills.csv";

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
        this.id = findNextId();
        this.employeeId = employeeId;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
        this.lastUsedDate = lastUsedDate;
    }

    // Helper Methods
    private static int findNextId() {
        List<EmployeeSkill> employeeSkills = getSkills();
        if (employeeSkills == null || employeeSkills.isEmpty()) {
            return 1;
        }
        return employeeSkills.get(employeeSkills.size() - 1).getId() + 1;
    }

    // Static Get, Save, Update, Delete Methods

    /**
     * Retrieves a list of all employee skills from the CSV file.
     *
     * @return A list of EmployeeSkill objects, or null if an error occurs.
     */
    public static List<EmployeeSkill> getSkills() {
        return CSVHelper.get(databasePath, EmployeeSkill.class);
    }

    /**
     * Retrieves a specific employee skill by the employee ID from the CSV file.
     *
     * @param employee_id The ID of the employee whose skill is to be retrieved.
     * @return The EmployeeSkill object with the specified employee ID, or null if
     *         not found or an error occurs.
     */
    public static EmployeeSkill getSkill(int employee_id) {
        return CSVHelper.get(databasePath, EmployeeSkill.class, employee_id);
    }

    /**
     * Sets a single employee skill in the CSV file.
     *
     * @param employeeSkill The EmployeeSkill object to set.
     */
    public static void saveSkill(EmployeeSkill employeeSkill) {
        CSVHelper.save(employeeSkill, databasePath, EmployeeSkill.class);
    }

    /**
     * Updates an existing employee skill in the CSV file.
     *
     * @param employeeSkill The EmployeeSkill object with updated information.
     */
    public static void updateSkill(EmployeeSkill employeeSkill) {
        CSVHelper.update(employeeSkill, databasePath, EmployeeSkill.class);
    }

    /**
     * Deletes an employee skill from the CSV file by its ID.
     *
     * @param id The ID of the employee skill to delete.
     */
    public static void deleteSkill(EmployeeSkill employeeSkill) {
        CSVHelper.delete(employeeSkill, databasePath, EmployeeSkill.class);
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

    @Override
    public String toString() {
        return "EmployeeSkill [id=" + id + ", employeeId=" + employeeId + ", skillName=" + skillName
                + ", proficiencyLevel=" + proficiencyLevel + ", yearsOfExperience=" + yearsOfExperience
                + ", lastUsedDate=" + lastUsedDate + "]";
    }
}