import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * Utility class for handling CSV operations using OpenCSV library.
 */
public final class CSVHelper {

	public CSVHelper() {
		throw new UnsupportedOperationException("Utility class");
	}

    /**
     * Reads a list of beans from a CSV file.
     *
     * @param <T>      the type of the bean
     * @param filePath the path to the CSV file
     * @param type     the class type of the bean
     * @return a list of beans read from the CSV file
     * @throws IOException if an I/O error occurs
     */
    public static <T> List<T> readBeansFromCsv(String filePath, Class<T> type) throws IOException {
        // Create CsvToBean instance to parse CSV file
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new FileReader(filePath))
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        // Parse the CSV file into a list of beans
        return csvToBean.parse();
    }

    /**
     * Writes a list of beans to a CSV file.
     *
     * @param <T>      the type of the bean
     * @param beans    the list of beans to write
     * @param filePath the path to the CSV file
     * @param type     the class type of the bean
     * @throws IOException if an I/O error occurs
     * @throws CsvDataTypeMismatchException if there is a data type mismatch
     * @throws CsvRequiredFieldEmptyException if a required field is empty
     */
    public static <T> void writeBeansToCsv(List<T> beans, String filePath, Class<T> type) 
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Create StatefulBeanToCsv instance to write beans to CSV file
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            // Write the list of beans to the CSV file
            beanToCsv.write(beans);
        }
    }
}