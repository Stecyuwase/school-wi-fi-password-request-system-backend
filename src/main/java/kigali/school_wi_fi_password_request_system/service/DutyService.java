package kigali.school_wi_fi_password_request_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.Duty;
import kigali.school_wi_fi_password_request_system.repository.DutyRepository;

@Service
public class DutyService {

    @Autowired
    private DutyRepository dutyRepo;

    public String saveDuty(Duty duty) {
        dutyRepo.save(duty);
        return "Duty saved successfully";
    }

    public List<Duty> getAllDuties() {
        return dutyRepo.findAll();
    }

    public Duty getDutyById(UUID id) {
        return dutyRepo.findById(id).orElse(null);
    }

    public String updateDuty(UUID id, Duty updated) {
        Duty duty = dutyRepo.findById(id).orElse(null);
        if (duty == null) {
            return "The duty with that id does not exist";
        }
        duty.setName(updated.getName());
        dutyRepo.save(duty);
        return "Duty updated successfully";
    }

    public String deleteDuty(UUID id) {
        Duty duty = dutyRepo.findById(id).orElse(null);
        if (duty == null) {
            return "The duty with that id does not exist";
        }
        dutyRepo.delete(duty);
        return "Duty deleted successfully";
    }

    // C3
    public List<Duty> getUnusedDuties() {
        return dutyRepo.findUnusedDuties();
    }
}