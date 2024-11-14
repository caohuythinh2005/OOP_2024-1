import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderExample {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("src\\hashtags.txt");
        BufferedReader reader = new BufferedReader(fr);
//        System.out.println(FileReaderExample.readAllLines(reader));
        ArrayList<String> arr = FileReaderExample.readALlLinesArrayList(reader);
        for (String x : arr) {
            System.out.println(x);
        }
    }

    public static String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        return content.toString();
    }

    public static ArrayList<String> readALlLinesArrayList(BufferedReader reader) throws IOException {
        ArrayList<String> content = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            content.add(line);
        }
        return content;
    }
}
