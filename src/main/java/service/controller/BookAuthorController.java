package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.service.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookAuthorController {

    private final BookService bookService;

    @Autowired
    public BookAuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAllAuthorBooks")
    public ResponseEntity getAllAuthorBooks() {
        try {
            return new ResponseEntity<>(bookService.getAllAuthorBooks(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
