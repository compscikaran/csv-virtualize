package dev.engineeringmadness.hephaestus.core.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;

    @Override
    public String toString() {
        return "Error: " + error + "\n" + message;
    }
}
