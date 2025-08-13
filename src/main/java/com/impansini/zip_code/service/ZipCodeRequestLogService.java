package com.impansini.zip_code.service;

import com.impansini.zip_code.domain.ZipCodeRequestLog;
import com.impansini.zip_code.repository.ZipCodeRequestLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ZipCodeRequestLogService {
    private final Logger log = LoggerFactory.getLogger(ZipCodeRequestLogService.class);
    private final ZipCodeRequestLogRepository zipCodeRequestLogRepository;

    public ZipCodeRequestLogService(ZipCodeRequestLogRepository zipCodeRequestLogRepository) {
        this.zipCodeRequestLogRepository = zipCodeRequestLogRepository;
    }

    public List<ZipCodeRequestLog> findAll() {
        log.debug("Request to get all zipCodeRequestLogs");
        return zipCodeRequestLogRepository.findAll();
    }

    public Optional<ZipCodeRequestLog> findOne(Long id) {
        log.debug("Request to get a zipCodeRequestLogs: {}", id);
        return zipCodeRequestLogRepository.findById(id);
    }

    @Transactional
    public List<ZipCodeRequestLog> findAllByInputZipCode(String inputZipCode) {
        log.debug("Request to get a zipCodeRequestLogs by inputZipCode: {}", inputZipCode);
        return zipCodeRequestLogRepository.findAllByInputZipCode(inputZipCode);
    }
}
