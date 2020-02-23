package service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @GetMapping("version")
    public ResponseEntity<String> getVersion() {
        return new ResponseEntity<>("1.0.0", HttpStatus.OK);
    }
}
