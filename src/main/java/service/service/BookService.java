package service.service;

public interface BookService {

    String getAllAuthorBooks();
    String getBookByID(String bookID);
    String getAuthorsBooks (String authorName);

}
