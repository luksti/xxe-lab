package xxe.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xxe.domain.Result;
import xxe.service.*;

@RestController
public class XmlController {

    private static final String SOLUTION_IS_INCORRECT = "Solution is incorrect";
    private final Lesson1Service lesson1Service;
    private final Lesson2Service lesson2Service;
    private final Lesson3Service lesson3Service;
    private final Lesson4Service lesson4Service;
    private final Lesson5Service lesson5Service;

    @Autowired
    public XmlController(Lesson1Service lesson1Service, Lesson2Service lesson2Service, Lesson3Service lesson3Service,
                         Lesson4Service lesson4Service, Lesson5Service lesson5Service) {
        this.lesson1Service = lesson1Service;
        this.lesson2Service = lesson2Service;
        this.lesson3Service = lesson3Service;
        this.lesson4Service = lesson4Service;
        this.lesson5Service = lesson5Service;
    }


    @PostMapping(value = "/xxe/lesson1/create")
    @ApiOperation("create")
    public ResponseEntity<Result> processXml(@RequestParam("file") MultipartFile file) {

        lesson1Service.processXml(file);
        return ResponseEntity.ok(Result.ok());
    }

    @GetMapping(value = "/xxe/lesson1/people")
    public ResponseEntity getPeople() {
        return ResponseEntity.ok(Result.ok(lesson1Service.getPeople()));
    }

    @GetMapping(value = "/xxe/lesson2/check")
    public ResponseEntity checkLesson2Solution() {
        return ResponseEntity.ok(lesson2Service.checkSolution() ? Result.ok() : Result.nok(SOLUTION_IS_INCORRECT));
    }

    @GetMapping(value = "/xxe/lesson3/check")
    public ResponseEntity checkLesson3Solution() {
        return ResponseEntity.ok(lesson3Service.checkSolution() ? Result.ok() : Result.nok(SOLUTION_IS_INCORRECT));
    }

    @GetMapping(value = "/xxe/lesson4/check")
    public ResponseEntity checkLesson4Solution() {
        return ResponseEntity.ok(lesson4Service.checkSolution() ? Result.ok() : Result.nok(SOLUTION_IS_INCORRECT));
    }

    @GetMapping(value = "/xxe/lesson5/check")
    public ResponseEntity checkLesson5Solution() {
        return ResponseEntity.ok(lesson5Service.checkSolution() ? Result.ok() : Result.nok(SOLUTION_IS_INCORRECT));
    }

}
