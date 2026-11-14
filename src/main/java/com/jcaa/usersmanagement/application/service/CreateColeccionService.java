package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateColeccionUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveColeccionPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateColeccionCommand;
import com.jcaa.usersmanagement.application.service.mapper.ColeccionApplicationMapper;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateColeccionService implements CreateColeccionUseCase {

  private final SaveColeccionPort saveColeccionPort;
  private final Validator validator;

  @Override
  public ColeccionModel execute(final CreateColeccionCommand command) {
    validateCommand(command);

    final ColeccionModel coleccionToSave =
        ColeccionApplicationMapper.fromCreateCommandToModel(command);

    return saveColeccionPort.save(coleccionToSave);
  }

  private void validateCommand(final CreateColeccionCommand command) {
    final Set<ConstraintViolation<CreateColeccionCommand>> violations =
        validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
