package kigali.school_wi_fi_password_request_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.Duty;

@Repository
public interface DutyRepository extends JpaRepository<Duty, UUID> {

    // C3 (JPQL): duties whose it staff collection is empty (IS EMPTY, no counting)
    @Query("SELECT d FROM Duty d WHERE d.itStaff IS EMPTY")
    List<Duty> findUnusedDuties();
}