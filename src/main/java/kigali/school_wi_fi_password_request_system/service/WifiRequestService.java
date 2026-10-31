package kigali.school_wi_fi_password_request_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.school_wi_fi_password_request_system.domain.ItStaff;
import kigali.school_wi_fi_password_request_system.domain.RequestStatus;
import kigali.school_wi_fi_password_request_system.domain.Student;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;
import kigali.school_wi_fi_password_request_system.repository.AccessGrantRepository;
import kigali.school_wi_fi_password_request_system.repository.ItStaffRepository;
import kigali.school_wi_fi_password_request_system.repository.StudentRepository;
import kigali.school_wi_fi_password_request_system.repository.WifiRequestRepository;

@Service
public class WifiRequestService {

    @Autowired
    private WifiRequestRepository reqRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ItStaffRepository staffRepo;

    @Autowired
    private AccessGrantRepository grantRepo;

    // D1: past date -> 400, staff already busy -> 409, status always PENDING
    public String saveWifiRequest(WifiRequest request, UUID studentId, UUID itStaffId) {
        if (request.getRequestDate() != null && request.getRequestDate().isBefore(LocalDate.now())) {
            return "Wifi request date cannot be in the past";
        }
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return "The student with that id does not exist";
        }
        ItStaff staff = staffRepo.findById(itStaffId).orElse(null);
        if (staff == null) {
            return "The it staff with that id does not exist";
        }
        if (reqRepo.existsByItStaffIdAndRequestDateAndStatusNot(itStaffId, request.getRequestDate(), RequestStatus.REJECTED)) {
            return "IT staff member already handles a request on that date";
        }
        request.setStudent(student);
        request.setItStaff(staff);
        request.setStatus(RequestStatus.PENDING);
        reqRepo.save(request);
        return "Wifi request saved successfully";
    }

    public List<WifiRequest> getAllWifiRequests() {
        return reqRepo.findAll();
    }

    public WifiRequest getWifiRequestById(UUID id) {
        return reqRepo.findById(id).orElse(null);
    }

    // The status is NOT changed here (D2: only the status endpoint changes it)
    public String updateWifiRequest(UUID id, WifiRequest updated) {
        WifiRequest request = reqRepo.findById(id).orElse(null);
        if (request == null) {
            return "The wifi request with that id does not exist";
        }
        request.setRequestDate(updated.getRequestDate());
        request.setReason(updated.getReason());
        reqRepo.save(request);
        return "Wifi request updated successfully";
    }

    // E3: blocked when the request has access grants
    public String deleteWifiRequest(UUID id) {
        WifiRequest request = reqRepo.findById(id).orElse(null);
        if (request == null) {
            return "The wifi request with that id does not exist";
        }
        if (grantRepo.existsByWifiRequest(request)) {
            return "That wifi request has access grants, delete them first";
        }
        reqRepo.delete(request);
        return "Wifi request deleted successfully";
    }

    // B2
    public List<WifiRequest> getByStatus(RequestStatus status) {
        return reqRepo.findByStatusOrderByRequestDateAsc(status);
    }

    // B3
    public List<WifiRequest> getBetween(LocalDate start, LocalDate end) {
        return reqRepo.findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    // D2: status flow
    public String changeStatus(UUID id, RequestStatus newStatus) {
        WifiRequest request = reqRepo.findById(id).orElse(null);
        if (request == null) {
            return "The wifi request with that id does not exist";
        }
        RequestStatus current = request.getStatus();
        boolean allowed =
                (current == RequestStatus.PENDING
                        && (newStatus == RequestStatus.REVIEWING || newStatus == RequestStatus.REJECTED))
             || (current == RequestStatus.REVIEWING
                        && (newStatus == RequestStatus.APPROVED || newStatus == RequestStatus.REJECTED));
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        request.setStatus(newStatus);
        reqRepo.save(request);
        return "Wifi request status updated successfully";
    }

    // F1
    public List<Object[]> getStatsByStatus() {
        return reqRepo.countGroupedByStatus();
    }

    // F2: one UPDATE query, no Java loop
    @Transactional
    public String cancelDay(UUID itStaffId, LocalDate date) {
        if (!staffRepo.existsById(itStaffId)) {
            return "The it staff with that id does not exist";
        }
        int count = reqRepo.cancelDay(itStaffId, date, RequestStatus.REJECTED, RequestStatus.APPROVED);
        return count + " wifi requests cancelled";
    }
}