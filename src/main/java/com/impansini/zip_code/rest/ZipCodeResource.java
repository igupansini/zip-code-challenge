package com.impansini.zip_code.rest;

import com.impansini.zip_code.domain.ZipCode;
import com.impansini.zip_code.repository.ZipCodeRepository;
import com.impansini.zip_code.service.ZipCodeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ZipCodeResource {

    private final Logger log = LoggerFactory.getLogger(ZipCodeResource.class);

    private final ZipCodeService zipCodeService;

    private final ZipCodeRepository zipCodeRepository;

    public ZipCodeResource(ZipCodeService zipCodeService, ZipCodeRepository zipCodeRepository) {
        this.zipCodeService = zipCodeService;
        this.zipCodeRepository = zipCodeRepository;
    }

    @PostMapping("/zip-codes")
    public ResponseEntity<ZipCode> createZipCode(@Valid @RequestBody ZipCode zipCode) throws URISyntaxException {
        log.debug("REST request to save a zipCode: {}", zipCode);
        if (zipCode.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new zipCode cannot already have an ID");
        }
        ZipCode result = zipCodeService.save(zipCode);
        return ResponseEntity
                .created(new URI("/api/zip-codes/" + result.getId()))
                .body(result);
    }

    @PutMapping("/zip-codes")
    public ResponseEntity<ZipCode> updateZipCode(@Valid @RequestBody ZipCode zipCode) {
        log.debug("REST request to update a zipCode: {}", zipCode);
        if (zipCode.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!zipCodeRepository.existsById(zipCode.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZipCode not found");
        }

        ZipCode updatedZipCode = zipCodeService.save(zipCode);
        return ResponseEntity
                .ok()
                .body(updatedZipCode);
    }

    @GetMapping("/zip-codes")
    public ResponseEntity<List<ZipCode>> getAllZipCodes() {
        log.debug("REST request to get all zipCodes");
        List<ZipCode> zipCodes = zipCodeService.findAll();
        return ResponseEntity.ok().body(zipCodes);
    }

    @GetMapping("/zip-codes/{id}")
    public ResponseEntity<Optional<ZipCode>> findOneZipCode(@PathVariable Long id) {
        log.debug("REST request to get a zipCode: {}", id);

        Optional<ZipCode> zipCode = zipCodeService.findOne(id);

        if (zipCode.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZipCode not found");
        }

        return ResponseEntity.ok().body(zipCode);
    }

    @DeleteMapping("/zip-codes/{id}")
    public ResponseEntity<Void> deleteZipCode(@PathVariable Long id) {
        log.debug("REST request to delete a zipCode: {}", id);
        zipCodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
