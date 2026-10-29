package kigali.school_wi_fi_password_request_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
}