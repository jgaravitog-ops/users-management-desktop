package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetColeccionByNumeroPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetColeccionByNumeroQuery;
import com.jcaa.usersmanagement.domain.exception.ColeccionNotFoundException;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetColeccionByNumeroService")
@ExtendWith(MockitoExtension.class)
class GetColeccionByNumeroServiceTest {

  @Mock private GetColeccionByNumeroPort getColeccionByNumeroPort;

  private GetColeccionByNumeroService service;

  @BeforeEach
  void setUp() {
    final ValidatorFactory validatorFactory =
        Validation.buildDefaultValidatorFactory();

    service =
        new GetColeccionByNumeroService(
            getColeccionByNumeroPort,
            validatorFactory.getValidator());
  }

  @Test
  @DisplayName("execute() retorna la colección cuando existe")
  void shouldReturnColeccionWhenFound() {
    // Arrange
    final GetColeccionByNumeroQuery query =
        new GetColeccionByNumeroQuery(1);

    final ColeccionModel expected =
        ColeccionModel.create(
            "OBR-001", "Editorial musical", 25000.0, 10, 2)
            .assignNumeroColeccion(1);

    when(getColeccionByNumeroPort.getByNumero(1))
        .thenReturn(Optional.of(expected));

    // Act
    final ColeccionModel result = service.execute(query);

    // Assert
    assertSame(expected, result);
    verify(getColeccionByNumeroPort).getByNumero(1);
  }

  @Test
  @DisplayName("execute() lanza ColeccionNotFoundException cuando no existe")
  void shouldThrowWhenColeccionNotFound() {
    // Arrange
    final GetColeccionByNumeroQuery query =
        new GetColeccionByNumeroQuery(404);

    when(getColeccionByNumeroPort.getByNumero(404))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        ColeccionNotFoundException.class,
        () -> service.execute(query));

    verify(getColeccionByNumeroPort).getByNumero(404);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el número es nulo")
  void shouldThrowWhenNumeroIsNull() {
    // Arrange
    final GetColeccionByNumeroQuery query =
        new GetColeccionByNumeroQuery(null);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(query));

    verifyNoInteractions(getColeccionByNumeroPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el número no es positivo")
  void shouldThrowWhenNumeroIsNotPositive() {
    // Arrange
    final GetColeccionByNumeroQuery query =
        new GetColeccionByNumeroQuery(0);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(query));

    verifyNoInteractions(getColeccionByNumeroPort);
  }
}
