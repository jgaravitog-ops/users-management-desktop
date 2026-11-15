package com.jcaa.usersmanagement.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import com.jcaa.usersmanagement.application.port.in.CreateColeccionUseCase;
import com.jcaa.usersmanagement.application.port.in.GetColeccionByNumeroUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateColeccionUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteColeccionUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateColeccionCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetColeccionByNumeroQuery;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateColeccionCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteColeccionCommand;
import com.jcaa.usersmanagement.domain.exception.ColeccionNotFoundException;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("DependencyContainer")
@ExtendWith(MockitoExtension.class)
class DependencyContainerTest {

  @Mock private Connection mockConnection;

  @Test
  @DisplayName(
      "constructor wires all dependencies and exposes a non-null UserController")
  void shouldWireAllDependenciesAndExposeNonNullUserController() {
    // Arrange
    try (final MockedStatic<DriverManager> mockedDriverManager =
        mockStatic(DriverManager.class)) {

      mockedDriverManager
          .when(
              () ->
                  DriverManager.getConnection(
                      any(String.class), any(String.class), any(String.class)))
          .thenReturn(mockConnection);

      // Act
      final DependencyContainer container = new DependencyContainer();

      // Assert
      assertAll(
          () -> assertNotNull(container.userController()),
          () -> assertNotNull(container.createColeccionUseCase()),
          () -> assertNotNull(container.getColeccionByNumeroUseCase()),
          () -> assertNotNull(container.updateColeccionUseCase()),
          () -> assertNotNull(container.deleteColeccionUseCase()));
    }
  }

  @Test
  @DisplayName("userController() returns the same UserController instance on every call")
  void shouldReturnTheSameUserControllerInstanceOnEveryCall() {
    // Arrange
    try (final MockedStatic<DriverManager> mockedDriverManager =
        mockStatic(DriverManager.class)) {

      mockedDriverManager
          .when(
              () ->
                  DriverManager.getConnection(
                      any(String.class), any(String.class), any(String.class)))
          .thenReturn(mockConnection);

      final DependencyContainer container = new DependencyContainer();

      // Act
      final UserController first = container.userController();
      final UserController second = container.userController();

      // Assert
      assertSame(first, second);
    }
  }

  @Test
  @DisplayName("constructor propagates PersistenceException when database connection fails")
  void shouldPropagatePersistenceExceptionWhenDatabaseConnectionFails() {
    // Arrange
    final SQLException cause = new SQLException("Connection refused");

    try (final MockedStatic<DriverManager> mockedDriverManager =
        mockStatic(DriverManager.class)) {

      mockedDriverManager
          .when(
              () ->
                  DriverManager.getConnection(
                      any(String.class), any(String.class), any(String.class)))
          .thenThrow(cause);

      // Act & Assert
      assertThrows(
          PersistenceException.class,
          DependencyContainer::new);
    }
  }

  @Test
  @DisplayName("Create, Get, Update and Delete use cases share the same in-memory repository")
  void shouldCreateRetrieveUpdateAndDeleteColeccionUsingSharedRepository() {
    // Arrange
    try (final MockedStatic<DriverManager> mockedDriverManager =
        mockStatic(DriverManager.class)) {

      mockedDriverManager
          .when(
              () ->
                  DriverManager.getConnection(
                      any(String.class), any(String.class), any(String.class)))
          .thenReturn(mockConnection);

      final DependencyContainer container = new DependencyContainer();

      final CreateColeccionCommand createCommand =
          new CreateColeccionCommand(
              "OBR-001", "Promotor A", 25000.0, 10, 2);

      // Act: crear colección
      final ColeccionModel created =
          container.createColeccionUseCase().execute(createCommand);

      // Act: actualizar colección
      final UpdateColeccionCommand updateCommand =
          new UpdateColeccionCommand(
              created.getNumeroColeccion(),
              "OBR-002",
              "Promotor B",
              30000.0,
              15,
              3);

      final ColeccionModel updated =
          container.updateColeccionUseCase().execute(updateCommand);

      // Act: consultar colección actualizada
      final ColeccionModel retrieved =
          container.getColeccionByNumeroUseCase()
              .execute(new GetColeccionByNumeroQuery(
                  created.getNumeroColeccion()));

      // Assert: verificar creación, actualización y consulta
      assertAll(
          "flujo de creación, actualización y consulta",
          () -> assertEquals(1, created.getNumeroColeccion()),
          () -> assertEquals(created.getNumeroColeccion(),
              updated.getNumeroColeccion()),
          () -> assertEquals(created.getNumeroColeccion(),
              retrieved.getNumeroColeccion()),
          () -> assertEquals("OBR-002", retrieved.getClaveObra()),
          () -> assertEquals("Promotor B", retrieved.getPromotor()),
          () -> assertEquals(30000.0, retrieved.getPvp()),
          () -> assertEquals(15, retrieved.getExistencias()),
          () -> assertEquals(3, retrieved.getStockMinimo()),
          () -> assertEquals(updated, retrieved));

      // Act: eliminar colección
      final DeleteColeccionCommand deleteCommand =
          new DeleteColeccionCommand(created.getNumeroColeccion());

      container.deleteColeccionUseCase().execute(deleteCommand);

      // Assert: comprobar que la colección ya no existe
      assertThrows(
          ColeccionNotFoundException.class,
          () -> container.getColeccionByNumeroUseCase()
              .execute(new GetColeccionByNumeroQuery(
                  created.getNumeroColeccion())));
    }
  }
}