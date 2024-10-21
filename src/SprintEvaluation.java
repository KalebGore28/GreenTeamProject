import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class SprintEvaluation {

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
	private int findNextId() {
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
	private static List<SprintEvaluation> getSprintEvaluations() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/sprint_evaluations.csv", SprintEvaluation.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves a single sprint evaluation by its ID.
	 *
	 * @param sprint_eval_id The ID of the sprint evaluation to retrieve.
	 * @return The SprintEvaluation object, or null if not found.
	 */
	public static SprintEvaluation getSprintEvaluation(int sprint_eval_id) {
		try {
			List<SprintEvaluation> sprint_evals = getSprintEvaluations();
			for (SprintEvaluation evaluation : sprint_evals) {
				if (evaluation.getId() == sprint_eval_id) {
					return evaluation;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Saves a list of sprint evaluations to the CSV file.
	 *
	 * @param evaluations The list of SprintEvaluation objects.
	 */
	public static void saveSprintEvaluations(List<SprintEvaluation> evaluations) {
		try {
			CSVHelper.writeBeansToCsv(evaluations, "src/databases/sprint_evaluations.csv", SprintEvaluation.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves a new sprint evaluation to the CSV file.
	 *
	 * @param evaluation The SprintEvaluation object to save.
	 */
	public static void saveSprintEvaluation(SprintEvaluation evaluation) {
		List<SprintEvaluation> evaluations = getSprintEvaluations();
		evaluations.add(evaluation);
		saveSprintEvaluations(evaluations);
	}

	/**
	 * Updates an existing sprint evaluation in the CSV file.
	 *
	 * @param evaluation The SprintEvaluation object to update.
	 */
	public static void updateSprintEvaluation(SprintEvaluation evaluation) {
		List<SprintEvaluation> evaluations = getSprintEvaluations();

		for (int i = 0; i < evaluations.size(); i++) {
			if (evaluations.get(i).getId() == evaluation.getId()) {
				evaluations.set(i, evaluation);
				break;
			}
		}

		saveSprintEvaluations(evaluations);
	}

	/**
	 * Deletes a sprint evaluation from the CSV file.
	 *
	 * @param evaluation The SprintEvaluation object to delete.
	 */
	public static void deleteSprintEvaluation(SprintEvaluation evaluation) {
		List<SprintEvaluation> evaluations = getSprintEvaluations();

		for (int i = 0; i < evaluations.size(); i++) {
			if (evaluations.get(i).getId() == evaluation.getId()) {
				evaluations.remove(i);
				break;
			}
		}

		saveSprintEvaluations(evaluations);
	}

	/**
	 * Creates a new instance of SprintEvaluation.
	 *
	 * @param sprintId   the ID of the sprint
	 * @param employeeId the ID of the employee
	 * @param date       the date of the evaluation
	 * @param rating     the rating given in the evaluation
	 * @param comment    additional comments for the evaluation
	 * @return a new SprintEvaluation object with the specified parameters
	 */
	public static SprintEvaluation newSprintEvaluation(int sprintId, int employeeId, String date, int rating,
			String comment) {
		return new SprintEvaluation(sprintId, employeeId, date, rating, comment);
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
