package etl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertThat;

public class ReadTransformWrite {

    private static JSONArray finalArr;
    private static final String filePath = "sample.xml";
    private static JSONObject xmlToJsonObj;

    //method to convert the input xml file to json object
    public static JSONObject convertXMLlToJson() {
        Path filePath = Paths.get(ReadTransformWrite.filePath);
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlToJsonObj = XML.toJSONObject(sb.toString());
        return xmlToJsonObj;
    }


    //method  to construct the required json object per specification
    public static JSONArray getAuthorBooks() {
        finalArr = new JSONArray();
        List<String> headers = Arrays.asList("BookID", "Title", "Genre", "Price", "PublishDate");
        JSONArray books = convertXMLlToJson().getJSONObject("catalog").optJSONArray("book");
        //authorTracker is the map to keep track of authors who've been encountered
        //the values of this map is the actual object that we need
        Map<String, JSONObject> authorTracker = new HashMap<>();

        for (int i = 0; i < books.length(); i++) {
            String authorName = books.getJSONObject(i).getString("author");

            if (!authorTracker.containsKey(authorName)) {
                JSONObject authorBooks = new JSONObject();

                authorBooks.put("Author", books.getJSONObject(i).getString("author"));
                authorBooks.put("Books", new ArrayList<>(Arrays.asList((headers), Arrays.asList(
                        books.getJSONObject(i).getString("id"),
                        books.getJSONObject(i).getString("title"),
                        books.getJSONObject(i).getString("genre"),
                        books.getJSONObject(i).getDouble("price"),
                        books.getJSONObject(i).getString("publish_date")
                ))));
                authorTracker.put(authorName, authorBooks);

            } else if (authorTracker.containsKey(authorName)) {
                authorTracker.get(authorName).getJSONArray("Books").put(
                        Arrays.asList(
                                books.getJSONObject(i).getString("id"),
                                books.getJSONObject(i).getString("title"),
                                books.getJSONObject(i).getString("genre"),
                                books.getJSONObject(i).getDouble("price"),
                                books.getJSONObject(i).getString("publish_date")
                        ));
            }
        }
        //finally iterate over the authorTracker map, pick the values and add to finalArray
        for (Map.Entry<String, JSONObject> entry: authorTracker.entrySet()) {
            finalArr.put(entry.getValue());
        }
        return finalArr;
    }

    //For a given bookID, provide book details.
    public static JSONArray getBookByID(String bookID) {
        JSONArray allBooks = convertXMLlToJson()
                .getJSONObject("catalog")
                .getJSONArray("book");
        JSONArray outputBooks = new JSONArray();

        allBooks.forEach(x -> {
            JSONObject book = (JSONObject) x;
            try {
                if (book.get("id").equals(bookID)) outputBooks.put(book);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return outputBooks;
    }

    public static void main(String[] args) {
        System.out.println(getBookByID("bk102").toString(3));
    }

}
