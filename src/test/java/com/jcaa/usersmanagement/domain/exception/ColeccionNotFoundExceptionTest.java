package com.jcaa.usersmanagement.domain.exception;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ColeccionNotFoundException")
class ColeccionNotFoundExceptionTest {

  @Test
  @DisplayName(
      "becauseNumberWasNotFound() debe incluir el número de colección en el mensaje")
  void shouldIncludeColeccionNumberInMessage() {
    // Arrange
    final Integer numeroColeccion = 404;

    // Act
    final String message =
        ColeccionNotFoundException
            .becauseNumberWasNotFound(numeroColeccion)
            .getMessage();

    // Assert
    assertTrue(
        message.contains(numeroColeccion.toString()),
        "el mensaje debe identificar el número de colección no encontrado");
  }
}
