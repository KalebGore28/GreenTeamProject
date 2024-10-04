import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class Tester {
    public static void main(String[] args) {
        try (CSVReader reader = CSVHelper.createCSVReader("src/databases/employees.csv")) {
            // Create employee objects from the CSV file
            List<Employee> employees = new ArrayList<>();
            String[] nextLine;

            // Skip the header row
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                Employee employee = new Employee(
                    Integer.parseInt(nextLine[0]), 
                    nextLine[1], 
                    nextLine[2], 
                    nextLine[3],
                    nextLine[4], 
                    nextLine[5], 
                    Double.parseDouble(nextLine[6])
                );
                employees.add(employee);
            }

            // Print the employee objects
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}