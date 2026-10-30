package kigali.school_wi_fi_password_request_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.WifiRequest;
import kigali.school_wi_fi_password_request_system.service.WifiRequestService;

@RestController
@RequestMapping("/api/wifi-requests")
public class WifiRequestController {

    @Autowired
    private WifiRequestService reqServe;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody WifiRequest request,
                                  @RequestParam UUID studentId,
                                  @RequestParam UUID itStaffId) {
        String msg = reqServe.saveWifiRequest(request, studentId, itStaffId);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.CREATED;
        return new ResponseEntity<>(msg, status);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WifiRequest>> getAll() {
        return new ResponseEntity<>(reqServe.getAllWifiRequests(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        WifiRequest request = reqServe.getWifiRequestById(id);
        if (request == null) {
            return new ResponseEntity<>("The wifi request with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody WifiRequest request) {
        String msg = reqServe.updateWifiRequest(id, request);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = reqServe.deleteWifiRequest(id);
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}