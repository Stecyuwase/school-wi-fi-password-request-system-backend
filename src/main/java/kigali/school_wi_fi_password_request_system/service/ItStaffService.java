package kigali.school_wi_fi_password_request_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.ComputerLab;
import kigali.school_wi_fi_password_request_system.domain.Duty;
import kigali.school_wi_fi_password_request_system.domain.ItStaff;
import kigali.school_wi_fi_password_request_system.repository.ComputerLabRepository;
import kigali.school_wi_fi_password_request_system.repository.DutyRepository;
import kigali.school_wi_fi_password_request_system.repository.ItStaffRepository;

@Service
public class ItStaffService {

    @Autowired
    private ItStaffRepository staffRepo;

    @Autowired
    private ComputerLabRepository labRepo;

    @Autowired
    private DutyRepository dutyRepo;

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

    // C1
    public List<ItStaff> getByDuty(String name) {
        return staffRepo.findByDutyName(name);
    }

    // D3: ItStaff owns the link, so we change ITS list and save ITS side
    public String addDuty(UUID itStaffId, UUID dutyId) {
        ItStaff staff = staffRepo.findById(itStaffId).orElse(null);
        if (staff == null) {
            return "The it staff with that id does not exist";
        }
        Duty duty = dutyRepo.findById(dutyId).orElse(null);
        if (duty == null) {
            return "That duty does not exist";
        }
        if (staff.getDuties().contains(duty)) {
            return "The it staff already has that duty";
        }
        staff.getDuties().add(duty);
        staffRepo.save(staff);
        return "Duty added to the it staff";
    }

    // D4: move an it staff to another lab
    public String changeLab(UUID itStaffId, int labNumber) {
        ItStaff staff = staffRepo.findById(itStaffId).orElse(null);
        if (staff == null) {
            return "The it staff with that id does not exist";
        }
        ComputerLab lab = labRepo.findByLabNumber(labNumber).orElse(null);
        if (lab == null) {
            return "The computer lab with that labNumber does not exist";
        }
        if (staffRepo.existsByComputerLabLabNumberAndIdNot(labNumber, itStaffId)) {
            return "That computer lab is taken by another it staff";
        }
        staff.setComputerLab(lab);
        staffRepo.save(staff);
        return "Computer lab changed successfully";
    }
}