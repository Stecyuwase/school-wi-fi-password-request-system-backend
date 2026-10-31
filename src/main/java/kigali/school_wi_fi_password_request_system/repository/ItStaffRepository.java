package kigali.school_wi_fi_password_request_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.ItStaff;

@Repository
public interface ItStaffRepository extends JpaRepository<ItStaff, UUID> {

    // C1 (JPQL)
    @Query("SELECT DISTINCT s FROM ItStaff s JOIN s.duties d WHERE LOWER(d.name) = LOWER(:name)")
    List<ItStaff> findByDutyName(@Param("name") String name);

    // D4 (DERIVED): is this lab used by another it staff?
    boolean existsByComputerLabLabNumberAndIdNot(int labNumber, UUID id);
}