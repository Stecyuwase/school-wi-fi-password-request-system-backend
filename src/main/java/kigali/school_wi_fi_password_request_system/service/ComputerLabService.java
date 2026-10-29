package kigali.school_wi_fi_password_request_system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.school_wi_fi_password_request_system.domain.ComputerLab;
import kigali.school_wi_fi_password_request_system.repository.ComputerLabRepository;

@Service
public class ComputerLabService {

    @Autowired
    private ComputerLabRepository labRepo;

    public String saveLab(ComputerLab lab) {
        labRepo.save(lab);
        return "Computer lab saved successfully";
    }

    public List<ComputerLab> getAllLabs() {
        return labRepo.findAll();
    }

    public ComputerLab getLabById(UUID id) {
        return labRepo.findById(id).orElse(null);
    }

    public String updateLab(UUID id, ComputerLab updated) {
        ComputerLab lab = labRepo.findById(id).orElse(null);
        if (lab == null) {
            return "The computer lab with that id does not exist";
        }
        lab.setLabNumber(updated.getLabNumber());
        lab.setName(updated.getName());
        labRepo.save(lab);
        return "Computer lab updated successfully";
    }

    public String deleteLab(UUID id) {
        ComputerLab lab = labRepo.findById(id).orElse(null);
        if (lab == null) {
            return "The computer lab with that id does not exist";
        }
        labRepo.delete(lab);
        return "Computer lab deleted successfully";
    }
}