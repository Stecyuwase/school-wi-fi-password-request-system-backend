package kigali.school_wi_fi_password_request_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.AccessGrant;
import kigali.school_wi_fi_password_request_system.service.AccessGrantService;

@RestController
@RequestMapping("/api/access-grants")
public class AccessGrantController {

    @Autowired
    private AccessGrantService grantServe;

    // E1
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AccessGrant grant, @RequestParam UUID wifiRequestId) {
        String msg = grantServe.saveGrant(grant, wifiRequestId);
        HttpStatus status;
        if (msg.contains("does not exist")) status = HttpStatus.NOT_FOUND;
        else if (msg.equals("Access grant saved successfully")) status = HttpStatus.CREATED;
        else status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(msg, status);
    }

    // E2
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AccessGrant>> getByStudent(@PathVariable String studentId) {
        return new ResponseEntity<>(grantServe.getByStudent(studentId), HttpStatus.OK);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = grantServe.deleteGrant(id);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}