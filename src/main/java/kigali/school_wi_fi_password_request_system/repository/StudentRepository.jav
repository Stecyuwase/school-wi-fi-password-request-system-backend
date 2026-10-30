package kigali.school_wi_fi_password_request_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    // B1 (DERIVED): same last name, ignoring letter case, sorted by first name A to Z
    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}