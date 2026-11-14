
package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ColeccionModel")
class ColeccionModelTest {

  private static final String CLAVE_OBRA = "OBR001";
  private static final String PROMOTOR = "Sony Music";
  private static final Double PVP = 45000.0;
  private static final Integer EXISTENCIAS = 20;
  private static final Integer STOCK_MINIMO = 5;

  // ── create()

  @Test
  @DisplayName("create() debe crear una colección sin número y preservar sus atributos")
  void shouldCreateColeccionAndPreserveAllFields() {

    // Act
    final ColeccionModel model =
        ColeccionModel.create(
            CLAVE_OBRA, PROMOTOR, PVP, EXISTENCIAS, STOCK_MINIMO);

    // Assert
    assertAll(
        "create() factory",
        () -> assertNull(model.getNumeroColeccion(), "el número debe asignarse al guardar"),
        () -> assertEquals(CLAVE_OBRA, model.getClaveObra()),
        () -> assertEquals(PROMOTOR, model.getPromotor()),
        () -> assertEquals(PVP, model.getPvp()),
        () -> assertEquals(EXISTENCIAS, model.getExistencias()),
        () -> assertEquals(STOCK_MINIMO, model.getStockMinimo()));
  }

  @Test
  @DisplayName("create() debe rechazar una clave de obra nula")
  void shouldRejectNullClaveObra() {

    // Act
    final IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> ColeccionModel.create(
                null, PROMOTOR, PVP, EXISTENCIAS, STOCK_MINIMO));

    // Assert
    assertEquals("La clave de obra es obligatoria.", exception.getMessage());
  }

  @Test
  @DisplayName("create() debe rechazar una clave de obra vacía")
  void shouldRejectEmptyClaveObra() {

    // Act
    assertThrows(
        IllegalArgumentException.class,
        () -> ColeccionModel.create(
            "", PROMOTOR, PVP, EXISTENCIAS, STOCK_MINIMO));
  }

  @Test
  @DisplayName("create() debe rechazar una clave de obra con espacios")
  void shouldRejectBlankClaveObra() {

    // Act
    assertThrows(
        IllegalArgumentException.class,
        () -> ColeccionModel.create(
            "   ", PROMOTOR, PVP, EXISTENCIAS, STOCK_MINIMO));
  }

  // ── assignNumeroColeccion()

  @Test
  @DisplayName("assignNumeroColeccion() debe retornar una nueva instancia con el número asignado")
  void shouldAssignNumeroColeccionAndPreserveOtherFields() {

    // Arrange
    final ColeccionModel original =
        ColeccionModel.create(
            CLAVE_OBRA, PROMOTOR, PVP, EXISTENCIAS, STOCK_MINIMO);

    // Act
    final ColeccionModel assigned = original.assignNumeroColeccion(1);

    // Assert
    assertAll(
        "resultado de assignNumeroColeccion()",
        () -> assertNotSame(original, assigned, "debe ser una nueva instancia"),
        () -> assertNull(original.getNumeroColeccion(), "el original debe permanecer sin número"),
        () -> assertEquals(1, assigned.getNumeroColeccion()),
        () -> assertEquals(original.getClaveObra(), assigned.getClaveObra()),
        () -> assertEquals(original.getPromotor(), assigned.getPromotor()),
        () -> assertEquals(original.getPvp(), assigned.getPvp()),
        () -> assertEquals(original.getExistencias(), assigned.getExistencias()),
        () -> assertEquals(original.getStockMinimo(), assigned.getStockMinimo()));
  }
}
