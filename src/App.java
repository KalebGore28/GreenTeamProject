import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        CSVReader reader = new CSVReaderBuilder(new FileReader("src/data.csv")).build();

        List<String[]> allLines = reader.readAll();

        for (String[] nextLine : allLines) {
            for (String token : nextLine) {
                System.out.print(token + " ");
            }
            System.out.println();
        }

        reader.close();
    }
}
