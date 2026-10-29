package kigali.school_wi_fi_password_request_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.ComputerLab;
import kigali.school_wi_fi_password_request_system.service.ComputerLabService;

@RestController
@RequestMapping("/api/computer-labs")
public class ComputerLabController {

    @Autowired
    private ComputerLabService labServe;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ComputerLab lab) {
        return new ResponseEntity<>(labServe.saveLab(lab), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ComputerLab>> getAll() {
        return new ResponseEntity<>(labServe.getAllLabs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ComputerLab lab = labServe.getLabById(id);
        if (lab == null) {
            return new ResponseEntity<>("The computer lab with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lab, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ComputerLab lab) {
        String msg = labServe.updateLab(id, lab);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = labServe.deleteLab(id);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}