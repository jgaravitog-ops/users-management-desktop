package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateColeccionCommand(

    @NotNull(message = "numeroColeccion must not be null")
    @Positive(message = "numeroColeccion must be positive")
    Integer numeroColeccion,

    @NotBlank(message = "claveObra must not be blank")
    String claveObra,

    String promotor,

    @NotNull(message = "pvp must not be null")
    Double pvp,

    @NotNull(message = "existencias must not be null")
    Integer existencias,

    Integer stockMinimo

) {
}
