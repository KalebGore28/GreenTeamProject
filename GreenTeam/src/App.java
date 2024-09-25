import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import java.io.FileReader;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("yourfile.csv"),
            new CSVParserBuilder()
                .withSeparator('\t')
                .build());
        
        // Example of reading data
        List<String[]> records = reader.readAll();
        for (String[] record : records) {
            System.out.println(String.join(", ", record));
        }
    }
}
