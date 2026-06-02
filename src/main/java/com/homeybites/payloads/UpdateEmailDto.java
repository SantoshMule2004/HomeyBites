package com.homeybites.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateEmailDto {
    @NotBlank
    @Email(regexp = ".*?@?[^@]*\\.+.*")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
