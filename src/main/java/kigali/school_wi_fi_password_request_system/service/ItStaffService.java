package kigali.school_wi_fi_password_request_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.ComputerLab;
import kigali.school_wi_fi_password_request_system.domain.ItStaff;
import kigali.school_wi_fi_password_request_system.repository.ComputerLabRepository;
import kigali.school_wi_fi_password_request_system.repository.ItStaffRepository;

@Service
public class ItStaffService {

    @Autowired
    private ItStaffRepository staffRepo;

    @Autowired
    private ComputerLabRepository labRepo;

    public String saveItStaff(ItStaff staff, UUID computerLabId) {
        if (computerLabId != null) {
            ComputerLab lab = labRepo.findById(computerLabId).orElse(null);
            if (lab == null) {
                return "The computer lab with that id does not exist";
            }
            staff.setComputerLab(lab);
        }
        staffRepo.save(staff);
        return "It staff saved successfully";
    }

    public List<ItStaff> getAllItStaff() {
        return staffRepo.findAll();
    }

    public ItStaff getItStaffById(UUID id) {
        return staffRepo.findById(id).orElse(null);
    }

    public String updateItStaff(UUID id, ItStaff updated) {
        ItStaff staff = staffRepo.findById(id).orElse(null);
        if (staff == null) {
            return "The it staff with that id does not exist";
        }
        staff.setFirstName(updated.getFirstName());
        staff.setLastName(updated.getLastName());
        staff.setEmail(updated.getEmail());
        staffRepo.save(staff);
        return "It staff updated successfully";
    }

    public String deleteItStaff(UUID id) {
        ItStaff staff = staffRepo.findById(id).orElse(null);
        if (staff == null) {
            return "The it staff with that id does not exist";
        }
        staffRepo.delete(staff);
        return "It staff deleted successfully";
    }
}