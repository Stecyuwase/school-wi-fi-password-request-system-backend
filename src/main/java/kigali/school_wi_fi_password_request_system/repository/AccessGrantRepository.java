package kigali.school_wi_fi_password_request_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.AccessGrant;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;

@Repository
public interface AccessGrantRepository extends JpaRepository<AccessGrant, UUID> {

    // E2 (JPQL): grants of a student, newest first
    @Query("SELECT g FROM AccessGrant g WHERE g.wifiRequest.student.studentId = :studentId "
         + "ORDER BY g.grantedDate DESC")
    List<AccessGrant> findByStudentBusinessId(@Param("studentId") String studentId);

    // E3 (DERIVED): does this wifi request have grants?
    boolean existsByWifiRequest(WifiRequest wifiRequest);
}