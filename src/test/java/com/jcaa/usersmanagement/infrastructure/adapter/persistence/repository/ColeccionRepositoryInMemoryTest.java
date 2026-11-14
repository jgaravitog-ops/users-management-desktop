package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ColeccionRepositoryInMemory")
class ColeccionRepositoryInMemoryTest {

  private ColeccionRepositoryInMemory repository;

  @BeforeEach
  void setUp() {
    repository = new ColeccionRepositoryInMemory();
  }

  @Test
  @DisplayName("save() asigna un número y almacena la colección")
  void shouldSaveColeccionWithGeneratedNumber() {
    // Arrange
    final ColeccionModel coleccion =
        ColeccionModel.create("OBR-001", "Editorial musical",
            25000.0, 10, 2);

    // Act
    final ColeccionModel saved = repository.save(coleccion);

    // Assert
    assertAll(
        () -> assertEquals(1, saved.getNumeroColeccion()),
        () -> assertEquals("OBR-001", saved.getClaveObra()),
        () -> assertEquals(25000.0, saved.getPvp()),
        () -> assertEquals(10, saved.getExistencias()));
  }

  @Test
  @DisplayName("save() genera números consecutivos")
  void shouldGenerateConsecutiveNumbers() {
    // Arrange
    final ColeccionModel first =
        ColeccionModel.create("OBR-001", null, 25000.0, 10, 2);
    final ColeccionModel second =
        ColeccionModel.create("OBR-002", null, 30000.0, 5, 1);

    // Act
    final ColeccionModel savedFirst = repository.save(first);
    final ColeccionModel savedSecond = repository.save(second);

    // Assert
    assertAll(
        () -> assertEquals(1, savedFirst.getNumeroColeccion()),
        () -> assertEquals(2, savedSecond.getNumeroColeccion()));
  }

  @Test
  @DisplayName("getByNumero() retorna la colección cuando existe")
  void shouldReturnColeccionWhenFound() {
    // Arrange
    final ColeccionModel coleccion =
        ColeccionModel.create("OBR-001", null, 25000.0, 10, 2);

    final ColeccionModel saved = repository.save(coleccion);

    // Act
    final Optional<ColeccionModel> result =
        repository.getByNumero(saved.getNumeroColeccion());

    // Assert
    assertTrue(result.isPresent());
    assertSame(saved, result.orElseThrow());
  }

  @Test
  @DisplayName("getByNumero() retorna vacío cuando no existe")
  void shouldReturnEmptyWhenNotFound() {
    // Act
    final Optional<ColeccionModel> result = repository.getByNumero(404);

    // Assert
    assertTrue(result.isEmpty());
  }
}
