package service.service.impl;

import etl.ReadTransformWrite;
import org.springframework.stereotype.Service;
import service.service.BookService;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public String getAllAuthorBooks() {
        return ReadTransformWrite.getAuthorBooks().toString();
    }

    @Override
    public String getBookByID(String bookID) {
        return ReadTransformWrite.getBooksByID(bookID).toString();
    }


    @Override
    public String getAuthorGenre(String authorName, String genre) {
        Map<String, String> inputParams = new HashMap<>();
        if (!authorName.equals("null")) inputParams.put("author", authorName);
        if (!genre.equals("null")) inputParams.put("genre", genre);

        //TODO - the following works. Why do I need RestTemplate?
        return ReadTransformWrite.getAuthorGenre(inputParams).toString();

        //TODO - What is the issue with the following piece of code?
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accepts", MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<?> request = new HttpEntity<>(inputParams, headers);
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:9090/api/v1/author",
//                HttpMethod.POST,
//                request,
//                String.class
//              );
//        String result = response.getBody();
//        return result;
    }
}
