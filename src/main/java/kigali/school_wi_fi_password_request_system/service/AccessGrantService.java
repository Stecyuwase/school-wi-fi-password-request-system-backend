package kigali.school_wi_fi_password_request_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.AccessGrant;
import kigali.school_wi_fi_password_request_system.domain.RequestStatus;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;
import kigali.school_wi_fi_password_request_system.repository.AccessGrantRepository;
import kigali.school_wi_fi_password_request_system.repository.WifiRequestRepository;

@Service
public class AccessGrantService {

    @Autowired
    private AccessGrantRepository grantRepo;

    @Autowired
    private WifiRequestRepository reqRepo;

    // E1
    public String saveGrant(AccessGrant grant, UUID wifiRequestId) {
        WifiRequest request = reqRepo.findById(wifiRequestId).orElse(null);
        if (request == null) {
            return "The wifi request with that id does not exist";
        }
        if (request.getStatus() != RequestStatus.APPROVED) {
            return "Access grants are only for APPROVED wifi requests";
        }
        grant.setWifiRequest(request);
        grant.setGrantedDate(LocalDate.now());
        grantRepo.save(grant);
        return "Access grant saved successfully";
    }

    // E2
    public List<AccessGrant> getByStudent(String studentId) {
        return grantRepo.findByStudentBusinessId(studentId);
    }

    // E3
    public String deleteGrant(UUID id) {
        AccessGrant grant = grantRepo.findById(id).orElse(null);
        if (grant == null) {
            return "The access grant with that id does not exist";
        }
        grantRepo.delete(grant);
        return "Access grant deleted successfully";
    }
}