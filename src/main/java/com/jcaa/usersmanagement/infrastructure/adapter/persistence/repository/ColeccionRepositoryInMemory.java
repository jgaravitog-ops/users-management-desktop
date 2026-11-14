package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.GetColeccionByNumeroPort;
import com.jcaa.usersmanagement.application.port.out.SaveColeccionPort;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ColeccionRepositoryInMemory
    implements SaveColeccionPort, GetColeccionByNumeroPort {

  private final Map<Integer, ColeccionModel> colecciones = new HashMap<>();
  private Integer nextNumeroColeccion = 1;

  @Override
  public ColeccionModel save(final ColeccionModel coleccion) {
    final Integer numeroColeccion = nextNumeroColeccion++;

    final ColeccionModel coleccionToSave =
        coleccion.assignNumeroColeccion(numeroColeccion);

    colecciones.put(numeroColeccion, coleccionToSave);

    return coleccionToSave;
  }

  @Override
  public Optional<ColeccionModel> getByNumero(
      final Integer numeroColeccion) {
    return Optional.ofNullable(colecciones.get(numeroColeccion));
  }
}
