package service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.service.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookAuthorController {
    private final Logger logger = LoggerFactory.getLogger(BookAuthorController.class);
    private final BookService bookService;

    @Autowired
    public BookAuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/allAuthorBooks", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBookByID(@RequestParam String bookID) {
        try {
            logger.info("getBookByID method has been invoked.");
            return new ResponseEntity(bookService.getBookByID(bookID), HttpStatus.OK);
        } catch (Exception e) {
            logger.debug("getBookByID method threw exception. Check book ID");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
    //TODO - tried with just (Required = False) and received nullpointer exception. Why?
    public ResponseEntity getAuthorDetails(@RequestParam(defaultValue = "null") String authorName,
                                           @RequestParam(defaultValue = "null") String genre) {
        try {
            logger.info("getAuthorDetails method has been invoked.");
            return new ResponseEntity<>(bookService.getAuthorGenre(authorName, genre), HttpStatus.OK);
        } catch (Exception e) {
        logger.debug("Exception thrown in getAuthorDetails method. Check value of author and genre.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
