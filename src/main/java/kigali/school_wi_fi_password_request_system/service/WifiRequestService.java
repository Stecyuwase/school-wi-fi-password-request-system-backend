package kigali.school_wi_fi_password_request_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.ItStaff;
import kigali.school_wi_fi_password_request_system.domain.RequestStatus;
import kigali.school_wi_fi_password_request_system.domain.Student;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;
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

    public String saveWifiRequest(WifiRequest request, UUID studentId, UUID itStaffId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return "The student with that id does not exist";
        }
        ItStaff staff = staffRepo.findById(itStaffId).orElse(null);
        if (staff == null) {
            return "The it staff with that id does not exist";
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

    // The status is NOT changed here (rule D2: only the status endpoint changes it)
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

    public String deleteWifiRequest(UUID id) {
        WifiRequest request = reqRepo.findById(id).orElse(null);
        if (request == null) {
            return "The wifi request with that id does not exist";
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
}