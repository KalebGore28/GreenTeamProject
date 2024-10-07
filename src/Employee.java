import com.opencsv.bean.CsvBindByName;

import java.io.IOException;
import java.util.List;

public class Employee {

	@CsvBindByName(column = "id")
	private int id;

	@CsvBindByName(column = "first_name")
	private String firstName;

	@CsvBindByName(column = "last_name")
	private String lastName;

	@CsvBindByName(column = "email")
	private String email;

	@CsvBindByName(column = "department")
	private String department;

	@CsvBindByName(column = "position")
	private String position;

	@CsvBindByName(column = "salary")
	private double salary;

	// Constructors
	public Employee() {
	}

	public Employee(int id, String firstName, String lastName, String email, String department,
			String position, double salary) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.position = position;
		this.salary = salary;
	}

	// Methods
	public static List<Employee> getEmployees() {
		try {
			return CSVHelper.readBeansFromCsv("src/databases/employees.csv", Employee.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}

	public String getPosition() {
		return position;
	}

	public double getSalary() {
		return salary;
	}

	// Setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", department=" + department + ", position=" + position + ", salary=" + salary + "]";
	}
}