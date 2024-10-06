import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    public void testGetEmployee() {
        Employee employee = Employee.getEmployee(100).get();

        assertNotNull(employee);

        assertEquals(100, employee.getId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals("Engineering", employee.getDepartment());
        assertEquals("Software Engineer", employee.getPosition());
        assertEquals(75000, employee.getSalary(), 0.001); // Use delta for floating-point comparison
    }

	@Test
	public void testGetEmployeeNonExistent() {
		assertEquals(false, Employee.getEmployee(1000).isPresent());
	}

	@Test
	public void testGetEmployeeInvalid() {
		assertEquals(false, Employee.getEmployee(-1).isPresent());
	}

	@Test
	public void testGetEmployees() {
		Employee[] employees = Employee.getEmployees(1,2).get();

		assertNotNull(employees);
		assertEquals(2, employees.length);

		assertEquals(100, employees[0].getId());
		assertEquals("John", employees[0].getFirstName());
		assertEquals("Doe", employees[0].getLastName());
		assertEquals("john.doe@example.com", employees[0].getEmail());
		assertEquals("Engineering", employees[0].getDepartment());
		assertEquals("Software Engineer", employees[0].getPosition());
		assertEquals(75000, employees[0].getSalary(), 0.001); // Use delta for floating-point comparison

		assertEquals(101, employees[1].getId());
		assertEquals("Jane", employees[1].getFirstName());
		assertEquals("Smith", employees[1].getLastName());
		assertEquals("jane.smith@example.com", employees[1].getEmail());
		assertEquals("Marketing", employees[1].getDepartment());
		assertEquals("Marketing Manager", employees[1].getPosition());
		assertEquals(90000, employees[1].getSalary(), 0.001); // Use delta for floating-point comparison
	}

	@Test
	public void testGetEmployeesNonExistent() {
		assertEquals(false, Employee.getEmployees(1000,1001).isPresent());
	}

	@Test
	public void testGetEmployeesInvalid() {
		assertEquals(false, Employee.getEmployees(-1,-2).isPresent());
	}

	@Test
	public void testSearchEmployees() {
		Employee[] employees = Employee.searchEmployees("salary", "75000.0", 3).get();

		assertNotNull(employees);
		assertEquals(3, employees.length);

		assertEquals(100, employees[0].getId());
		assertEquals("John", employees[0].getFirstName());
		assertEquals("Doe", employees[0].getLastName());
		assertEquals("john.doe@example.com", employees[0].getEmail());
		assertEquals("Engineering", employees[0].getDepartment());
		assertEquals("Software Engineer", employees[0].getPosition());
		assertEquals(75000, employees[0].getSalary(), 0.001); // Use delta for floating-point comparison

		assertEquals(103, employees[1].getId());
		assertEquals("Michael", employees[1].getFirstName());
		assertEquals("Brown", employees[1].getLastName());
		assertEquals("michael.brown@example.com", employees[1].getEmail());
		assertEquals("HR", employees[1].getDepartment());
		assertEquals("HR Manager", employees[1].getPosition());
		assertEquals(75000, employees[1].getSalary(), 0.001); // Use delta for floating-point comparison

		assertEquals(104, employees[2].getId());
		assertEquals("Jessica", employees[2].getFirstName());
		assertEquals("Johnson", employees[2].getLastName());
		assertEquals("jessica.johnson@example.com", employees[2].getEmail());
		assertEquals("Finance", employees[2].getDepartment());
		assertEquals("Finance Manager", employees[2].getPosition());
		assertEquals(75000, employees[2].getSalary(), 0.001); // Use delta for floating-point comparison
	}
}