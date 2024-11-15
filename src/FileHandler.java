import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static List<String> readFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    public static void writeFile(String filePath, List<String> lines) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }
}
