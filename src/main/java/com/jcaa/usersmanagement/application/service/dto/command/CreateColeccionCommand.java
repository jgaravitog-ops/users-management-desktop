
package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateColeccionCommand(

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
