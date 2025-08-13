package com.impansini.zip_code.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public ZipCodeRequestLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getInputZipCode() {
        return inputZipCode;
    }

    public void setInputZipCode(String inputZipCode) {
        this.inputZipCode = inputZipCode;
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ZipCodeRequestLog that = (ZipCodeRequestLog) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ZipCodeRequestLog{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", inputZipCode='" + inputZipCode + '\'' +
                ", apiResponse='" + apiResponse + '\'' +
                '}';
    }
}
