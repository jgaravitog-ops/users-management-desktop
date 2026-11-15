package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetColeccionByNumeroPort;
import com.jcaa.usersmanagement.application.port.out.UpdateColeccionPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateColeccionCommand;
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

@DisplayName("UpdateColeccionService")
@ExtendWith(MockitoExtension.class)
class UpdateColeccionServiceTest {

  @Mock private UpdateColeccionPort updateColeccionPort;

  @Mock private GetColeccionByNumeroPort getColeccionByNumeroPort;

  private UpdateColeccionService service;

  @BeforeEach
  void setUp() {
    final ValidatorFactory validatorFactory =
        Validation.buildDefaultValidatorFactory();

    service =
        new UpdateColeccionService(
            updateColeccionPort,
            getColeccionByNumeroPort,
            validatorFactory.getValidator());
  }

  @Test
  @DisplayName("execute() actualiza la colección cuando existe y los datos son válidos")
  void shouldUpdateColeccionWhenCommandIsValid() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            1, "OBR-002", "Nueva editorial", 30000.0, 15, 3);

    final ColeccionModel existingColeccion =
        ColeccionModel.create(
            "OBR-001", "Editorial musical", 25000.0, 10, 2)
            .assignNumeroColeccion(1);

    final ColeccionModel updatedColeccion =
        ColeccionModel.create(
            "OBR-002", "Nueva editorial", 30000.0, 15, 3)
            .assignNumeroColeccion(1);

    when(getColeccionByNumeroPort.getByNumero(1))
        .thenReturn(Optional.of(existingColeccion));

    when(updateColeccionPort.update(any(ColeccionModel.class)))
        .thenReturn(updatedColeccion);

    // Act
    final ColeccionModel result = service.execute(command);

    // Assert
    assertAll(
        "flujo feliz de UpdateColeccionService",
        () -> assertNotNull(result),
        () -> assertEquals(1, result.getNumeroColeccion()),
        () -> assertEquals("OBR-002", result.getClaveObra()),
        () -> assertEquals("Nueva editorial", result.getPromotor()),
        () -> assertEquals(30000.0, result.getPvp()),
        () -> assertEquals(15, result.getExistencias()),
        () -> assertEquals(3, result.getStockMinimo()));

    verify(getColeccionByNumeroPort).getByNumero(1);
    verify(updateColeccionPort).update(any(ColeccionModel.class));
  }

  @Test
  @DisplayName("execute() lanza ColeccionNotFoundException cuando no existe")
  void shouldThrowWhenColeccionNotFound() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            404, "OBR-002", "Nueva editorial", 30000.0, 15, 3);

    when(getColeccionByNumeroPort.getByNumero(404))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        ColeccionNotFoundException.class,
        () -> service.execute(command));

    verify(getColeccionByNumeroPort).getByNumero(404);
    verifyNoInteractions(updateColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el número es nulo")
  void shouldThrowWhenNumeroIsNull() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            null, "OBR-002", "Nueva editorial", 30000.0, 15, 3);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(getColeccionByNumeroPort, updateColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el número no es positivo")
  void shouldThrowWhenNumeroIsNotPositive() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            0, "OBR-002", "Nueva editorial", 30000.0, 15, 3);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(getColeccionByNumeroPort, updateColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando la clave de obra está vacía")
  void shouldThrowWhenClaveObraIsBlank() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            1, " ", "Nueva editorial", 30000.0, 15, 3);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(getColeccionByNumeroPort, updateColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el PVP es nulo")
  void shouldThrowWhenPvpIsNull() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            1, "OBR-002", "Nueva editorial", null, 15, 3);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(getColeccionByNumeroPort, updateColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando las existencias son nulas")
  void shouldThrowWhenExistenciasIsNull() {
    // Arrange
    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            1, "OBR-002", "Nueva editorial", 30000.0, null, 3);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(getColeccionByNumeroPort, updateColeccionPort);
  }
}

