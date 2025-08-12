package com.impansini.zip_code.service;

import com.impansini.zip_code.domain.ZipCode;
import com.impansini.zip_code.repository.ZipCodeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZipCodeService {
    private final Logger log = LoggerFactory.getLogger(ZipCodeService.class);

    private final ZipCodeRepository zipCodeRepository;

    public ZipCodeService(ZipCodeRepository zipCodeRepository) {
        this.zipCodeRepository = zipCodeRepository;
    }

    @Transactional
    public ZipCode save(ZipCode zipCode) {
        log.debug("Request to save a zipCode: {}", zipCode);
        return zipCodeRepository.save(zipCode);
    }

    public List<ZipCode> findAll() {
        log.debug("Request to get all zipCodes");
        return zipCodeRepository.findAll();
    }

    public Optional<ZipCode> findOne(Long id) {
        log.debug("Request to get a zipCode: {}", id);
        return zipCodeRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        zipCodeRepository
                .findById(id)
                .ifPresent(zipCode -> {
                    zipCodeRepository.delete(zipCode);
                    log.debug("Deleted zipCode: {}", zipCode);
                });
    }
}
