package service.service.impl;

import etl.ReadTransformWrite;
import org.springframework.stereotype.Service;
import service.service.BookService;

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
    public String getAuthorsBooks(String authorName) {
        return null;
    }
}
