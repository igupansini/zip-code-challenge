package com.impansini.zip_code.rest;

import com.impansini.zip_code.domain.ZipCodeRequestLog;
import com.impansini.zip_code.service.ZipCodeRequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ZipCodeRequestLogResource {
    private final Logger log = LoggerFactory.getLogger(ZipCodeRequestLogResource.class);

    private final ZipCodeRequestLogService zipCodeRequestLogService;

    public ZipCodeRequestLogResource(ZipCodeRequestLogService zipCodeRequestLogService) {
        this.zipCodeRequestLogService = zipCodeRequestLogService;
    }

    @GetMapping("/request-logs")
    public ResponseEntity<List<ZipCodeRequestLog>> getAllZipCodeRequestLog() {
        log.debug("REST request to get all ZipCodeRequestLog");
        List<ZipCodeRequestLog> requestLogs = zipCodeRequestLogService.findAll();
        return ResponseEntity.ok().body(requestLogs);
    }

    @GetMapping("/request-logs/{id}")
    public ResponseEntity<Optional<ZipCodeRequestLog>> findOneZipCodeRequestLog(@PathVariable Long id) {
        log.debug("REST request to get a ZipCodeRequestLog: {}", id);

        Optional<ZipCodeRequestLog> requestLog = zipCodeRequestLogService.findOne(id);

        if (requestLog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        return ResponseEntity.ok().body(requestLog);
    }

    @GetMapping("/request-logs/zip/{zip}")
    public ResponseEntity<List<ZipCodeRequestLog>> findOneZipCodeRequestLogByZip(@PathVariable String zip) {
        log.debug("REST request to get a ZipCodeRequestLog by zip: {}", zip);

        List<ZipCodeRequestLog> requestLog = zipCodeRequestLogService.findAllByInputZipCode(zip);

        if (requestLog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        return ResponseEntity.ok().body(requestLog);
    }
}
