import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ReadTransformWrite {

    //Tasks
    // - Read the file
    // - Extract the names of all the Authors and books
    // - Create a JSON object at the author level.
    // - Sample provided below
    /*
        {
            "Author": "Corets, Eva",
            "Books": [
                ["BookID", "Title", "Genre", "Price", "PublishDate"],
                ["bk103", "Maeve Ascendant", "Fantasy", 5.95, "2000-11-17"],
                ["bk104", "Oberon's Legacy", "Fantasy", 5.95, "2001 - 03 - 10"]
            ]
        }
     */

    public static void main(String[] args) {
        readAndTrandformAndWrite();
    }

    public static void readAndTrandformAndWrite() {
        Path filepath = Paths.get("sample.xml");
        List<String> linesRead = null;
        try {
            linesRead = Files.lines(filepath)
                    .filter(x -> x.contains("author"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(linesRead.toString());
    }


}
