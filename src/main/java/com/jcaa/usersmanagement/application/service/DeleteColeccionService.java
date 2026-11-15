package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteColeccionUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteColeccionPort;
import com.jcaa.usersmanagement.application.port.out.GetColeccionByNumeroPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteColeccionCommand;
import com.jcaa.usersmanagement.domain.exception.ColeccionNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class DeleteColeccionService implements DeleteColeccionUseCase {

  private final DeleteColeccionPort deleteColeccionPort;
  private final GetColeccionByNumeroPort getColeccionByNumeroPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteColeccionCommand command) {
    validateCommand(command);

    final Integer numeroColeccion = command.numeroColeccion();
    ensureColeccionExists(numeroColeccion);
    deleteColeccionPort.delete(numeroColeccion);
  }

  private void validateCommand(final DeleteColeccionCommand command) {
    final Set<ConstraintViolation<DeleteColeccionCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureColeccionExists(final Integer numeroColeccion) {
    getColeccionByNumeroPort
        .getByNumero(numeroColeccion)
        .orElseThrow(() ->
            ColeccionNotFoundException.becauseNumberWasNotFound(numeroColeccion));
  }
}