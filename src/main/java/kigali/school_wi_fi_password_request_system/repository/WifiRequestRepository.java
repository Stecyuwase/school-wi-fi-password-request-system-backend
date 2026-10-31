package kigali.school_wi_fi_password_request_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.RequestStatus;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;

@Repository
public interface WifiRequestRepository extends JpaRepository<WifiRequest, UUID> {

    // B2 (DERIVED)
    List<WifiRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    // B3 (DERIVED)
    List<WifiRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

    // D1 (DERIVED)
    boolean existsByItStaffIdAndRequestDateAndStatusNot(UUID itStaffId, LocalDate date, RequestStatus status);

    // F1 (JPQL): one row per status with its count
    @Query("SELECT w.status, COUNT(w) FROM WifiRequest w GROUP BY w.status")
    List<Object[]> countGroupedByStatus();

    // F2 (JPQL UPDATE): reject every request of that it staff on that date, except APPROVED
    @Modifying
    @Query("UPDATE WifiRequest w SET w.status = :rejected "
         + "WHERE w.itStaff.id = :itStaffId AND w.requestDate = :date AND w.status <> :approved")
    int cancelDay(@Param("itStaffId") UUID itStaffId,
                  @Param("date") LocalDate date,
                  @Param("rejected") RequestStatus rejected,
                  @Param("approved") RequestStatus approved);
}