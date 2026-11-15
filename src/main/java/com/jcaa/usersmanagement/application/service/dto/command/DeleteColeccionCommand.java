package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeleteColeccionCommand(
    @NotNull(message = "numeroColeccion must not be null")
    @Positive(message = "numeroColeccion must be positive")
    Integer numeroColeccion
) {
}