package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GetColeccionByNumeroQuery(

    @NotNull(message = "numeroColeccion must not be null")
    @Positive(message = "numeroColeccion must be positive")
    Integer numeroColeccion

) {
}
