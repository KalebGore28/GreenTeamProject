import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("src/data.csv"));
        List<String[]> data = reader.readAll();
        for (String[] row : data) {
            System.out.println(row[0] + " " + row[1] + " " + row[2]);
        }
        reader.close();

    }
}
