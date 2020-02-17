package service.service.impl;

import etl.ReadTransformWrite;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    public String getAuthorDetails(String authorName) {
        return null;
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
//        headers.set("Accepts", MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<?> request = new HttpEntity<>(inputParams, headers);
//        HttpEntity<String> response = restTemplate.exchange("http://localhost:9090/api/v1/author", HttpMethod.POST, request, new ParameterizedTypeReference<String>() {
//        });
//        String result = response.getBody();
//        return result;
    }
}
