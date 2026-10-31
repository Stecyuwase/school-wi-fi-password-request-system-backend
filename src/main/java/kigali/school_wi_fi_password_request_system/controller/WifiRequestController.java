package kigali.school_wi_fi_password_request_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.school_wi_fi_password_request_system.domain.RequestStatus;
import kigali.school_wi_fi_password_request_system.domain.WifiRequest;
import kigali.school_wi_fi_password_request_system.service.WifiRequestService;

@RestController
@RequestMapping("/api/wifi-requests")
public class WifiRequestController {

    @Autowired
    private WifiRequestService reqServe;

    // D1
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody WifiRequest request,
                                  @RequestParam UUID studentId,
                                  @RequestParam UUID itStaffId) {
        String msg = reqServe.saveWifiRequest(request, studentId, itStaffId);
        HttpStatus status;
        if (msg.contains("does not exist")) status = HttpStatus.NOT_FOUND;
        else if (msg.contains("past")) status = HttpStatus.BAD_REQUEST;
        else if (msg.contains("already handles")) status = HttpStatus.CONFLICT;
        else status = HttpStatus.CREATED;
        return new ResponseEntity<>(msg, status);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WifiRequest>> getAll() {
        return new ResponseEntity<>(reqServe.getAllWifiRequests(), HttpStatus.OK);
    }

    // B2
    @GetMapping("/by-status")
    public ResponseEntity<List<WifiRequest>> getByStatus(@RequestParam RequestStatus status) {
        return new ResponseEntity<>(reqServe.getByStatus(status), HttpStatus.OK);
    }

    // B3
    @GetMapping("/between")
    public ResponseEntity<List<WifiRequest>> getBetween(@RequestParam String start,
                                                        @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(reqServe.getBetween(startDate, endDate), HttpStatus.OK);
    }

    // F1
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> statsByStatus() {
        return new ResponseEntity<>(reqServe.getStatsByStatus(), HttpStatus.OK);
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

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = reqServe.deleteWifiRequest(id);
        HttpStatus status;
        if (msg.contains("does not exist")) status = HttpStatus.NOT_FOUND;
        else if (msg.contains("has access grants")) status = HttpStatus.CONFLICT;
        else status = HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam RequestStatus status) {
        String msg = reqServe.changeStatus(id, status);
        HttpStatus httpStatus;
        if (msg.contains("does not exist")) httpStatus = HttpStatus.NOT_FOUND;
        else if (msg.equals("Wifi request status updated successfully")) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(msg, httpStatus);
    }

    // F2
    @PatchMapping("/cancel-day")
    public ResponseEntity<?> cancelDay(@RequestParam UUID itStaffId, @RequestParam String date) {
        String msg = reqServe.cancelDay(itStaffId, LocalDate.parse(date));
        HttpStatus status = msg.contains("does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(msg, status);
    }
}