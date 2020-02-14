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
}
