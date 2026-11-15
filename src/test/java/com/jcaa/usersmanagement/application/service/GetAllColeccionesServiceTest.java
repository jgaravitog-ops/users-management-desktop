package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllColeccionesPort;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllColeccionesService")
@ExtendWith(MockitoExtension.class)
class GetAllColeccionesServiceTest {

  @Mock private GetAllColeccionesPort getAllColeccionesPort;

  private GetAllColeccionesService service;

  @BeforeEach
  void setUp() {
    service = new GetAllColeccionesService(getAllColeccionesPort);
  }

  @Test
  @DisplayName("execute() retorna la lista de colecciones del puerto")
  void shouldReturnColeccionesFromPort() {
    // Arrange
    final ColeccionModel first =
        ColeccionModel.create("OBR-001", "Promotor A", 25000.0, 10, 2)
            .assignNumeroColeccion(1);

    final ColeccionModel second =
        ColeccionModel.create("OBR-002", "Promotor B", 30000.0, 5, 1)
            .assignNumeroColeccion(2);

    when(getAllColeccionesPort.getAll()).thenReturn(List.of(first, second));

    // Act
    final List<ColeccionModel> result = service.execute();

    // Assert
    assertAll(
        "getAll con dos colecciones",
        () -> assertEquals(2, result.size(), "debe retornar dos colecciones"),
        () -> assertSame(first, result.get(0), "debe conservar la primera colección"),
        () -> assertSame(second, result.get(1), "debe conservar la segunda colección"));

    verify(getAllColeccionesPort).getAll();
  }

  @Test
  @DisplayName("execute() retorna lista vacía cuando no hay colecciones")
  void shouldReturnEmptyListWhenNoColecciones() {
    // Arrange
    when(getAllColeccionesPort.getAll()).thenReturn(List.of());

    // Act
    final List<ColeccionModel> result = service.execute();

    // Assert
    assertTrue(result.isEmpty(), "debe retornar lista vacía");

    verify(getAllColeccionesPort).getAll();
  }
}
