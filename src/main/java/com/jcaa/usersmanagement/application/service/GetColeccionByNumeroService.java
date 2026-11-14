package com.jcaa.usersmanagement.application.service;

import java.util.Set;

import com.jcaa.usersmanagement.application.port.in.GetColeccionByNumeroUseCase;
import com.jcaa.usersmanagement.application.port.out.GetColeccionByNumeroPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetColeccionByNumeroQuery;
import com.jcaa.usersmanagement.application.service.mapper.ColeccionApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ColeccionNotFoundException;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetColeccionByNumeroService
    implements GetColeccionByNumeroUseCase {

  private final GetColeccionByNumeroPort getColeccionByNumeroPort;
  private final Validator validator;

  @Override
  public ColeccionModel execute(final GetColeccionByNumeroQuery query) {
    validateQuery(query);

    final Integer numeroColeccion =
        ColeccionApplicationMapper
            .fromGetColeccionByNumeroQueryToNumero(query);

    return getColeccionByNumeroPort
        .getByNumero(numeroColeccion)
        .orElseThrow(
            () -> ColeccionNotFoundException
                .becauseNumberWasNotFound(numeroColeccion));
  }

  private void validateQuery(final GetColeccionByNumeroQuery query) {
    final Set<ConstraintViolation<GetColeccionByNumeroQuery>> violations =
        validator.validate(query);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
