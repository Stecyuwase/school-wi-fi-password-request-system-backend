package kigali.school_wi_fi_password_request_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.school_wi_fi_password_request_system.domain.RequestStatus;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;

@Repository
public interface WifiRequestRepository extends JpaRepository<WifiRequest, UUID> {

    // B2 (DERIVED)
    List<WifiRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    // B3 (DERIVED)
    List<WifiRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

    // D1 (DERIVED): has this it staff a request on that date whose status is not REJECTED?
    boolean existsByItStaffIdAndRequestDateAndStatusNot(UUID itStaffId, LocalDate date, RequestStatus status);
}