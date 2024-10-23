import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class SprintEvaluation {
	private static final String databasePath = "src/databases/sprint_evaluations.csv";

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "sprint_id")
	private int sprintId;

	@CsvBindByName(column = "employee_id")
	private int employeeId;

	@CsvBindByName(column = "date")
	private String date;

	@CsvBindByName(column = "rating")
	private int rating;

	@CsvBindByName(column = "comment")
	private String comment;

	// Constructors

	public SprintEvaluation() {
	}

	/**
	 * Parameterized constructor to initialize a SprintEvaluation object.
	 *
	 * @param sprintId   The ID of the sprint being evaluated.
	 * @param employeeId The ID of the employee being evaluated.
	 * @param date       The date of the evaluation.
	 * @param rating     The rating given to the employee for the sprint.
	 * @param comments   Additional comments or feedback for the employee.
	 */
	public SprintEvaluation(int sprintId, int employeeId, String date, int rating, String comment) {
		this.id = findNextId();
		this.sprintId = sprintId;
		this.employeeId = employeeId;
		this.date = date;
		this.rating = rating;
		this.comment = comment;
	}

	// Helper Methods

	/**
	 * Finds the next available ID for a new sprint evaluation.
	 *
	 * @return The next available ID.
	 */
	private static int findNextId() {
		List<SprintEvaluation> evaluations = getSprintEvaluations();
		int nextId = 1;
		for (SprintEvaluation evaluation : evaluations) {
			if (evaluation.getId() >= nextId) {
				nextId = evaluation.getId() + 1;
			}
		}
		return nextId;
	}

	// Static Get, Save, Update, Delete, New Sprint Evaluation Methods

	/**
	 * Retrieves a list of all sprint evaluations.
	 *
	 * @return A list of all sprint evaluations.
	 */
	public static List<SprintEvaluation> getSprintEvaluations() {
		return CSVHelper.get(databasePath, SprintEvaluation.class);
	}

	/**
	 * Retrieves a single sprint evaluation by its ID.
	 *
	 * @param sprint_eval_id The ID of the sprint evaluation to retrieve.
	 * @return The SprintEvaluation object, or null if not found.
	 */
	public static SprintEvaluation getSprintEvaluation(int sprint_eval_id) {
		return CSVHelper.get(databasePath, SprintEvaluation.class, sprint_eval_id);
	}

	/**
	 * Saves a new sprint evaluation to the CSV file.
	 *
	 * @param evaluation The SprintEvaluation object to save.
	 */
	public static void saveSprintEvaluation(SprintEvaluation evaluation) {
		CSVHelper.save(evaluation, databasePath, SprintEvaluation.class);
	}

	/**
	 * Updates an existing sprint evaluation in the CSV file.
	 *
	 * @param evaluation The SprintEvaluation object to update.
	 */
	public static void updateSprintEvaluation(SprintEvaluation evaluation) {
		CSVHelper.update(evaluation, databasePath, SprintEvaluation.class);
	}

	/**
	 * Deletes a sprint evaluation from the CSV file.
	 *
	 * @param evaluation The SprintEvaluation object to delete.
	 */
	public static void deleteSprintEvaluation(SprintEvaluation evaluation) {
		CSVHelper.delete(evaluation, databasePath, SprintEvaluation.class);
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

	public String getDate() {
		return date;
	}

	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	// Setters
	public void setDate(String date) {
		this.date = date;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "SprintEvaluation{" + "id=" + id + ", sprintId=" + sprintId + ", employeeId=" + employeeId + ", date='"
				+ date + '\'' + ", rating=" + rating + ", comment='" + comment + '\'' + '}';
	}
}
