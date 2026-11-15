
package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateColeccionUseCase;
import com.jcaa.usersmanagement.application.port.out.GetColeccionByNumeroPort;
import com.jcaa.usersmanagement.application.port.out.UpdateColeccionPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateColeccionCommand;
import com.jcaa.usersmanagement.application.service.mapper.ColeccionApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ColeccionNotFoundException;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import java.util.Set;

@RequiredArgsConstructor
public final class UpdateColeccionService implements UpdateColeccionUseCase {

  private final UpdateColeccionPort updateColeccionPort;
  private final GetColeccionByNumeroPort getColeccionByNumeroPort;
  private final Validator validator;

  @Override
  public ColeccionModel execute(final UpdateColeccionCommand command) {
    validateCommand(command);

    final Integer numeroColeccion =
        ColeccionApplicationMapper.fromUpdateCommandToNumero(command);

    findExistingColeccionOrFail(numeroColeccion);

    final ColeccionModel coleccionToUpdate =
        ColeccionApplicationMapper.fromUpdateCommandToModel(command);

    return updateColeccionPort.update(coleccionToUpdate);
  }

  private void validateCommand(final UpdateColeccionCommand command) {
    final Set<ConstraintViolation<UpdateColeccionCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void findExistingColeccionOrFail(final Integer numeroColeccion) {
    getColeccionByNumeroPort
        .getByNumero(numeroColeccion)
        .orElseThrow(
            () -> ColeccionNotFoundException.becauseNumberWasNotFound(numeroColeccion));
  }
}
