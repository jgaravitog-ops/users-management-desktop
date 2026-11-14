package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.SaveColeccionPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateColeccionCommand;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateColeccionService")
@ExtendWith(MockitoExtension.class)
class CreateColeccionServiceTest {

  @Mock private SaveColeccionPort saveColeccionPort;

  private CreateColeccionService service;

  @BeforeEach
  void setUp() {
    final ValidatorFactory validatorFactory =
        Validation.buildDefaultValidatorFactory();

    service =
        new CreateColeccionService(
            saveColeccionPort,
            validatorFactory.getValidator());
  }

  @Test
  @DisplayName("execute() guarda la colección cuando los datos son válidos")
  void shouldSaveColeccionWhenCommandIsValid() {
    // Arrange
    final CreateColeccionCommand command =
        new CreateColeccionCommand("OBR-001", "Editorial musical",
            25000.0, 10, 2);

    final ColeccionModel savedColeccion =
        ColeccionModel.create(
            "OBR-001", "Editorial musical", 25000.0, 10, 2)
            .assignNumeroColeccion(1);

    when(saveColeccionPort.save(any(ColeccionModel.class)))
        .thenReturn(savedColeccion);

    // Act
    final ColeccionModel result = service.execute(command);

    // Assert
    assertAll(
        "flujo feliz de CreateColeccionService",
        () -> assertNotNull(result, "el resultado no debe ser null"),
        () -> assertEquals(1, result.getNumeroColeccion()),
        () -> assertEquals("OBR-001", result.getClaveObra()),
        () -> assertEquals(25000.0, result.getPvp()),
        () -> assertEquals(10, result.getExistencias()));

    verify(saveColeccionPort).save(any(ColeccionModel.class));
  }

  @Test
  @DisplayName("execute() lanza excepción cuando la clave de obra está vacía")
  void shouldThrowWhenClaveObraIsBlank() {
    // Arrange
    final CreateColeccionCommand command =
        new CreateColeccionCommand(" ", "Editorial musical",
            25000.0, 10, 2);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(saveColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando el PVP es nulo")
  void shouldThrowWhenPvpIsNull() {
    // Arrange
    final CreateColeccionCommand command =
        new CreateColeccionCommand("OBR-001", "Editorial musical",
            null, 10, 2);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(saveColeccionPort);
  }

  @Test
  @DisplayName("execute() lanza excepción cuando las existencias son nulas")
  void shouldThrowWhenExistenciasIsNull() {
    // Arrange
    final CreateColeccionCommand command =
        new CreateColeccionCommand("OBR-001", "Editorial musical",
            25000.0, null, 2);

    // Act & Assert
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(command));

    verifyNoInteractions(saveColeccionPort);
  }
}

