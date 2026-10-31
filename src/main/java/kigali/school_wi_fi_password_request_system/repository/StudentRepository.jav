package kigali.school_wi_fi_password_request_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    // B1 (DERIVED)
    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4 (JPQL): each student once, who has a wifi request with this it staff
    @Query("SELECT DISTINCT w.student FROM WifiRequest w WHERE w.itStaff.id = :itStaffId")
    List<Student> findStudentsOfItStaff(@Param("itStaffId") UUID itStaffId);
}