package com.impansini.zip_code.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "zip_code_request_logs")
public class ZipCodeRequestLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime requestTime = LocalDateTime.now();

    @NotNull
    @Column(nullable = false, length = 10)
    private String inputZipCode;

    @Lob // Large Object
    private String apiResponse;
}
