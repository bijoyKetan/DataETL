package service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.service.BookService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BookAuthorController {
    private final Logger logger = LoggerFactory.getLogger(BookAuthorController.class);
    private final BookService bookService;

    @Autowired
    public BookAuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/allAuthorBooks")
    public ResponseEntity getAllAuthorBooks() {
        try {
            logger.info("The getAllAuthorBoks method has been invoked");
            return new ResponseEntity<>(bookService.getAllAuthorBooks(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("getAllAuthorBooks method threw an exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book")
    public ResponseEntity getBookByID(@RequestParam String bookID) {
        try {
            return new ResponseEntity(bookService.getBookByID(bookID), HttpStatus.OK);
        } catch (Exception e) {
            logger.debug("getBookByID method threw exception. Check book ID");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/author")
    //TODO - tried with just (Required = False) and received nullpointer exception. Why?
    public ResponseEntity getAuthorDetails(@RequestParam(defaultValue = "null") String authorName,
                                           @RequestParam(defaultValue = "null") String genre) {
        try {
            return new ResponseEntity<>(bookService.getAuthorGenre(authorName, genre), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
