package kigali.school_wi_fi_password_request_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.Duty;
import kigali.school_wi_fi_password_request_system.service.DutyService;

@RestController
@RequestMapping("/api/duties")
public class DutyController {

    @Autowired
    private DutyService dutyServe;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Duty duty) {
        return new ResponseEntity<>(dutyServe.saveDuty(duty), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Duty>> getAll() {
        return new ResponseEntity<>(dutyServe.getAllDuties(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Duty duty = dutyServe.getDutyById(id);
        if (duty == null) {
            return new ResponseEntity<>("The duty with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(duty, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Duty duty) {
        String msg = dutyServe.updateDuty(id, duty);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = dutyServe.deleteDuty(id);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}