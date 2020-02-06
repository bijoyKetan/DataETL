import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReadTransformWrite {

    //Tasks
    // - Read the file
    // - Extract the names of all the Authors and books
    // - Create a JSON object at the author level.
    // - Sample provided below
    /*
        [{
            "Author": "Corets, Eva",
            "Books": [
                ["BookID", "Title", "Genre", "Price", "PublishDate"],
                ["bk103", "Maeve Ascendant", "Fantasy", 5.95, "2000-11-17"],
                ["bk104", "Oberon's Legacy", "Fantasy", 5.95, "2001 - 03 - 10"]
            ]
        },
        {
          "Author": "Gambardella, Matthew",
          "Books": [
            ["BookID", "Title", "Genre", "Price", "PublishDate"],
            ["bk101", "XML Developer's Guide", "Computer", 44.95, "2000-10-01"]
          ]
        }]
     */

    public static void main(String[] args) {
//        System.out.println(convertXMLlToJson().getJSONObject("catalog").optJSONArray("book").toString(3));
//        JSONArray testArr = convertXMLlToJson().getJSONObjectct("catalog").optJSONArray("book");
//        System.out.println(testArr.toString(3));
        System.out.println(reConstructJson().toString(3));

    }

    //method to convert the input xml file to json object
    public static JSONObject convertXMLlToJson() {
        Path filePath = Paths.get("sample.xml");
        StringBuilder sb = new StringBuilder();
        JSONObject jn;

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jn = XML.toJSONObject(sb.toString());
        return jn;
    }

    //method  to reconstruct the required json object
    public static JSONArray reConstructJson() {
        JSONArray finalArr = new JSONArray();
        List<String> headers = Arrays.asList("BookID", "Title", "Genre", "Price", "PublishDate");
        JSONArray books = convertXMLlToJson().getJSONObject("catalog").optJSONArray("book");

        for (int i = 0; i < books.length(); i++) {
            JSONObject authorBooks = new JSONObject();
            authorBooks.put("Author", books.getJSONObject(i).getString("author"));
            authorBooks.put("Books", new JSONArray()
                    .put(headers)
                    .put(books.getJSONObject(i).getString("id"))
                    .put(books.getJSONObject((i)).getString("title"))
                    .put(books.getJSONObject(i).getString("genre"))
                    .put(books.getJSONObject(i).getDouble("price"))
                    .put(books.getJSONObject(i).getString("publish_date"))
            );
            finalArr.put(authorBooks);
        }
        return finalArr;
    }
}
