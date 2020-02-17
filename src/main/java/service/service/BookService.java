package service.service;

import java.util.Map;

public interface BookService {

    String getAllAuthorBooks();
    String getBookByID(String bookID);
    String getAuthorDetails(String authorName);
    String getAuthorGenre (String authorName, String genre);

}
