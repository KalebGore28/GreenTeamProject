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

    private CSVHelper() {
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
     * @throws IOException                    if an I/O error occurs
     * @throws CsvDataTypeMismatchException   if there is a data type mismatch
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

    /**
     * Reads a list of beans from a CSV file.
     *
     * @param <T>      the type of the bean
     * @param filePath the path to the CSV file
     * @param type     the class type of the
     * @return a list of beans read from the CSV file
     */
    public static <T> List<T> get(String filePath, Class<T> type) {
        try {
            return CSVHelper.readBeansFromCsv(filePath, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a single bean by its ID from a CSV file.
     *
     * @param <T>      the type of the bean
     * @param filePath the path to the CSV file
     * @param type     the class type of the
     * @param id       the ID of the bean to retrieve
     * @return the bean with the specified ID, or null if not found
     */
    public static <T> T get(String filePath, Class<T> type, int id) {
        List<T> beans = get(filePath, type);
        for (T bean : beans) {
            if (bean instanceof Identifiable) {
                Identifiable identifiable = (Identifiable) bean;
                if (identifiable.getId() == id) {
                    return bean;
                }
            }
        }
        return null;
    }

    /**
     * Saves a list of beans to a CSV file.
     *
     * @param <T>      the type of the bean
     * @param beans    the list of beans to save
     * @param filePath the path to the CSV file
     * @param type     the class type of the bean
     */
    public static <T> void save(List<T> beans, String filePath, Class<T> type) {
        try {
            CSVHelper.writeBeansToCsv(beans, filePath, type);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a single bean to a CSV file.
     *
     * @param <T>      the type of the bean
     * @param bean     the bean to save
     * @param filePath the path to the CSV file
     * @param type     the class type of the bean
     */
    public static <T> void save(T bean, String filePath, Class<T> type) {
        List<T> beans = get(filePath, type);
        beans.add(bean);
        save(beans, filePath, type);
    }

    /**
     * Updates a single bean in a CSV file.
     *
     * @param <T>      the type of the bean
     * @param bean     the bean to update
     * @param filePath the path to the CSV file
     * @param type     the class type of the bean
     */
    public static <T> void update(T bean, String filePath, Class<T> type) {
        List<T> beans = get(filePath, type);
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i) instanceof Identifiable) {
                Identifiable identifiable = (Identifiable) beans.get(i);
                if (identifiable.getId() == ((Identifiable) bean).getId()) {
                    beans.set(i, bean);
                    save(beans, filePath, type);
                    return;
                }
            }
        }
    }

    /**
     * Deletes a single bean from a CSV file.
     *
     * @param <T>      the type of the bean
     * @param bean     the bean to delete
     * @param filePath the path to the CSV file
     * @param type     the class type of the bean
     */
    public static <T> void delete(T bean, String filePath, Class<T> type) {
        List<T> beans = get(filePath, type);
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i) instanceof Identifiable) {
                Identifiable identifiable = (Identifiable) beans.get(i);
                if (identifiable.getId() == ((Identifiable) bean).getId()) {
                    beans.remove(i);
                    save(beans, filePath, type);
                    return;
                }
            }
        }
    }

}