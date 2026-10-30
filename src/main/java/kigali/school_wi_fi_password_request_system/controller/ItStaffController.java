package kigali.school_wi_fi_password_request_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.ItStaff;
import kigali.school_wi_fi_password_request_system.service.ItStaffService;

@RestController
@RequestMapping("/api/it-staff")
public class ItStaffController {

    @Autowired
    private ItStaffService staffServe;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ItStaff staff,
                                  @RequestParam(required = false) UUID computerLabId) {
        String msg = staffServe.saveItStaff(staff, computerLabId);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.CREATED;
        return new ResponseEntity<>(msg, status);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItStaff>> getAll() {
        return new ResponseEntity<>(staffServe.getAllItStaff(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ItStaff staff = staffServe.getItStaffById(id);
        if (staff == null) {
            return new ResponseEntity<>("The it staff with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ItStaff staff) {
        String msg = staffServe.updateItStaff(id, staff);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = staffServe.deleteItStaff(id);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}