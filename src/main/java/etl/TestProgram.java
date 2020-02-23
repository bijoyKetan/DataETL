package etl;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TestProgram {

//    public static void main(String[] args) {
////        String output = FileConverter.readAndPrintFile().toString();
////        System.out.println(output);
////        FileConverter.readLineJava8();
//        TestProgram.writeLineJava8();
//    }

    private static Map<String, String> lineMap = new HashMap<>();
    private static List<String> lineList = new ArrayList<>();
    private static JSONObject jo = new JSONObject();


    //    Read content of a file with Java 8 way
    public static void readLineJava8() {
        //Specify the filepath using Path class
        Path filePath = Paths.get("sample.xml");
        // Read with Files.lines method inside a try/catch block
        try {
            Stream<String> lines = Files.lines(filePath);
            lines
                    .filter(x -> x.contains("book"))
                    .forEach(System.out::println);
            //Close the stream
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Write to a file using Java 8
    public static void writeLineJava8() {
        //Create the path
        Path writePath = Paths.get("sampleOutput.txt");
        //Create buffered writer
        try (BufferedWriter writer = Files.newBufferedWriter(writePath)) {
            writer.write("123");
            writer.write("\n456");
            writer.write("\n789");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Read file line by line and add to the list - This is the Java 7 way
    public static List<String> readAndPrintFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/Users/ketan/Downloads/sample.xml"));
            while (br.readLine() != null) {
                lineList.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }
}
