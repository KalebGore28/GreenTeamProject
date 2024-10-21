import com.opencsv.bean.CsvBindByName;
import java.util.List;

public class Sprint {

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "name")
	private String name;

	@CsvBindByName(column = "start_date")
	private String startDate;

	@CsvBindByName(column = "end_date")
	private String endDate;

	@CsvBindByName(column = "status")
	private String status;

	@CsvBindByName(column = "velocity")
	private int velocity;

	public Sprint() {
	}

	/**
	 * Parameterized constructor to initialize a Sprint object.
	 *
	 * @param name      The name of the sprint.
	 * @param startDate The start date of the sprint.
	 * @param endDate   The end date of the sprint.
	 * @param status    The status of the sprint.
	 * @param velocity  The velocity of the sprint.
	 */
	public Sprint(String name, String startDate, String endDate, String status, int velocity) {
		this.id = findNextId();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.velocity = velocity;
	}

	// Helper Methods

	/**
	 * Finds the next available ID for a new sprint.
	 *
	 * @return The next available ID.
	 */
	private static int findNextId() {
		List<Sprint> sprints = getSprints();
		int nextId = 1;
		for (Sprint sprint : sprints) {
			if (sprint.getId() >= nextId) {
				nextId = sprint.getId() + 1;
			}
		}
		return nextId;
	}

	// Static Get, Save, Update, Delete, New Sprint Methods

	/**
	 * Retrieves a list of all sprints from the CSV file.
	 *
	 * @return A list of Sprint objects.
	 */
	public static List<Sprint> getSprints() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/sprints.csv", Sprint.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves a single sprint by its ID.
	 *
	 * @param sprint_id The ID of the sprint to retrieve.
	 * @return The Sprint object, or null if the sprint is not found.
	 */
	public static Sprint getSprint(int sprint_id) {
		try {
			List<Sprint> sprints = getSprints();
			for (Sprint sprint : sprints) {
				if (sprint.getId() == sprint_id) {
					return sprint;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Saves a list of sprints to the CSV file.
	 *
	 * @param sprints The list of Sprint objects.
	 */
	public static void saveSprints(List<Sprint> sprints) {
		try {
			CSVHelper.writeBeansToCsv(sprints, "src/databases/sprints.csv", Sprint.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves a new sprint to the CSV file.
	 *
	 * @param sprint The new Sprint object.
	 */
	public static void saveSprint(Sprint sprint) {
		List<Sprint> sprints = getSprints();
		sprints.add(sprint);
		saveSprints(sprints);
	}

	/**
	 * Updates a sprint in the CSV file.
	 *
	 * @param sprint The updated Sprint object.
	 */
	public static void updateSprint(Sprint sprint) {
		List<Sprint> sprints = getSprints();

		// Find the sprint in the list and replace it
		for (int i = 0; i < sprints.size(); i++) {
			if (sprints.get(i).getId() == sprint.getId()) {
				sprints.set(i, sprint);
				break;
			}
		}

		saveSprints(sprints);
	}

	/**
	 * Deletes a sprint from the CSV file.
	 *
	 * @param sprint_id The ID of the sprint to delete.
	 */
	public static void deleteSprint(int sprint_id) {
		List<Sprint> sprints = getSprints();

		// Find the sprint in the list and remove it
		for (int i = 0; i < sprints.size(); i++) {
			if (sprints.get(i).getId() == sprint_id) {
				sprints.remove(i);
				break;
			}
		}

		saveSprints(sprints);
	}

	/**
	 * Creates a new Sprint object with the given parameters.
	 *
	 * @param name      The name of the sprint.
	 * @param startDate The start date of the sprint.
	 * @param endDate   The end date of the sprint.
	 * @param status    The status of the sprint.
	 * @param velocity  The velocity of the sprint.
	 * @return The new Sprint object.
	 */
	public static Sprint newSprint(String name, String startDate, String endDate, String status, int velocity) {
		return new Sprint(name, startDate, endDate, status, velocity);
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getStatus() {
		return status;
	}

	public int getVelocity() {
		return velocity;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "Sprint{" +
				"id=" + id +
				", name='" + name + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", status='" + status + '\'' +
				", velocity=" + velocity +
				'}';
	}

}
