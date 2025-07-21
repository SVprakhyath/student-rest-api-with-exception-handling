package com.development.demo.rest;

import com.development.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentDemoController {

    private List<Student> theStudents;

    // define postConstruct method to load the student data only once
    @PostConstruct
    public void loadData(){
        theStudents = new ArrayList<>();
        theStudents.add(new Student("John","Doe"));
        theStudents.add(new Student("Mary","Smith"));
        theStudents.add(new Student("Susan","Johnson"));
    }

    // define the endpoint for students - return the list of students
    @GetMapping("/students")
    public List<Student> getStudents(){
        return theStudents;
    }

    //define endpoint for "/students/{studentId}" to return a single student
    @GetMapping("/student/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        // check the student id with size of the list
        if((studentId >= theStudents.size()) || (studentId < 0)){
            throw new StudentNotFoundException("student id not found -" + studentId);
        }
        return theStudents.get(studentId);
    }


    // Add exception handler using @ExceptionHandler annotation
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){

        // create a StudentErrorResponse object
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity with error object and HttpStatus

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){

        // create a StudentErrorResponse object
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity with error object and HttpStatus

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
