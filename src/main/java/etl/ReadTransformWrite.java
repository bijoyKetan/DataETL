package etl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    //a private helper method to get all yhe books in a JSON array
    private static JSONArray getAllBooksinJSON() {
        return convertXMLlToJson()
                .getJSONObject("catalog")
                .getJSONArray("book");
    }

    //For a given bookID, provide book details.
    public static JSONArray getBooksByID(String bookID) {
        JSONArray allBooks = getAllBooksinJSON();
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

    //Get author details
    //TODO - How to formulate functionas that can work with various number of argumnets? Passing a JSON object as parameter?
    //TODO - Why can't I use regulat stream with JSONArray? I can's invoke the JSONObject.getString function; why is that?
    public static List<Object> getAuthorDetails(String author) {
        JSONArray books = getAllBooksinJSON();
        return IntStream.range(0, books.length())
                .filter(i -> books.getJSONObject(i).getString("author").equals(author))
                .mapToObj(i -> books.get(i))
                .collect(Collectors.toList());
    }

    //method works with variable number of parameters, by author or by genre or both.
    public static List<Object> getAuthorGenre(Map<String, String> inputs) {
        JSONArray books = getAllBooksinJSON();
        String inputAuthor = inputs.get("author");
        String inputGenre = inputs.get("genre");

        if (inputs.containsKey("author") && inputs.containsKey("genre")) {
            return IntStream.range(0, books.length())
                    .filter(i -> books.getJSONObject(i).getString("author").equals(inputAuthor)
                            && books.getJSONObject(i).getString("genre").equals(inputGenre))
                    .mapToObj(i -> books.get(i))
                    .collect(Collectors.toList());
        } else if (inputs.containsKey("author") && !inputs.containsKey("genre")) {
            return IntStream.range(0, books.length())
                    .filter(i -> books.getJSONObject(i).getString("author").equals(inputAuthor))
                    .mapToObj(i -> books.get(i))
                    .collect(Collectors.toList());
        } else if (inputs.containsKey("genre") && !inputs.containsKey("author")) {
            return IntStream.range(0, books.length())
                    .filter(i -> books.getJSONObject(i).getString("genre").equals(inputGenre))
                    .mapToObj(i -> books.get(i))
                    .collect(Collectors.toList());
        }
        return null;
    }

    //For testing purposes.
    public static void main(String[] args) {
//        System.out.println(getAuthorDetails("Corets, Eva"));
        Map<String, String> testMap = new HashMap<>();
//        testMap.put("author", "Corets, Eva");
//        testMap.put("genre", "Romance");
        System.out.println(getAuthorGenre(testMap));
        //TODO What is the issue with this? Does not compile.
//        System.out.println(getAuthorDetails(Map.of("author", "Corets, Eva", "genre", "Fantasy")));
    }

}
