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



}
