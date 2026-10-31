package kigali.school_wi_fi_password_request_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.Student;
import kigali.school_wi_fi_password_request_system.repository.ItStaffRepository;
import kigali.school_wi_fi_password_request_system.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ItStaffRepository staffRepo;

    public String saveStudent(Student student) {
        studentRepo.save(student);
        return "Student saved successfully";
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student getStudentById(UUID id) {
        return studentRepo.findById(id).orElse(null);
    }

    public String updateStudent(UUID id, Student updated) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return "The student with that id does not exist";
        }
        student.setFirstName(updated.getFirstName());
        student.setLastName(updated.getLastName());
        student.setStudentId(updated.getStudentId());
        student.setClassName(updated.getClassName());
        studentRepo.save(student);
        return "Student updated successfully";
    }

    public String deleteStudent(UUID id) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return "The student with that id does not exist";
        }
        studentRepo.delete(student);
        return "Student deleted successfully";
    }

    // B1
    public List<Student> getByLastName(String lastName) {
        return studentRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4 (null if the it staff does not exist)
    public List<Student> getStudentsOfItStaff(UUID itStaffId) {
        if (!staffRepo.existsById(itStaffId)) {
            return null;
        }
        return studentRepo.findStudentsOfItStaff(itStaffId);
    }
}