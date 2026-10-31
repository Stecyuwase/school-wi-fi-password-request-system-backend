package kigali.school_wi_fi_password_request_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.Student;
import kigali.school_wi_fi_password_request_system.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentServe;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Student student) {
        return new ResponseEntity<>(studentServe.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAll() {
        return new ResponseEntity<>(studentServe.getAllStudents(), HttpStatus.OK);
    }

    // B1
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Student>> getByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(studentServe.getByLastName(lastName), HttpStatus.OK);
    }

    // C4
    @GetMapping("/of-it-staff/{itStaffId}")
    public ResponseEntity<?> getOfItStaff(@PathVariable UUID itStaffId) {
        List<Student> list = studentServe.getStudentsOfItStaff(itStaffId);
        if (list == null) {
            return new ResponseEntity<>("The it staff with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Student student = studentServe.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Student student) {
        String msg = studentServe.updateStudent(id, student);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = studentServe.deleteStudent(id);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}