package kigali.school_wi_fi_password_request_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.ComputerLab;

@Repository
public interface ComputerLabRepository extends JpaRepository<ComputerLab, UUID> {

    // C2 (JPQL): labs that no it staff occupies
    @Query("SELECT l FROM ComputerLab l WHERE NOT EXISTS "
         + "(SELECT s FROM ItStaff s WHERE s.computerLab = l) ORDER BY l.labNumber")
    List<ComputerLab> findFreeLabs();
}