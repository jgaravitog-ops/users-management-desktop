
package com.jcaa.usersmanagement.domain.model;

import lombok.Value;

@Value
public class ColeccionModel {

  Integer numeroColeccion;
  String claveObra;
  String promotor;
  Double pvp;
  Integer existencias;
  Integer stockMinimo;

  public static ColeccionModel create(
      final String claveObra,
      final String promotor,
      final Double pvp,
      final Integer existencias,
      final Integer stockMinimo) {

    validateClaveObra(claveObra);

    return new ColeccionModel(
        null,
        claveObra.trim(),
        promotor,
        pvp,
        existencias,
        stockMinimo);
  }

  public ColeccionModel assignNumeroColeccion(final Integer numeroColeccion) {
    return new ColeccionModel(
        numeroColeccion,
        claveObra,
        promotor,
        pvp,
        existencias,
        stockMinimo);
  }

  private static void validateClaveObra(final String claveObra) {
    if (claveObra == null || claveObra.isBlank()) {
      throw new IllegalArgumentException(
          "La clave de obra es obligatoria.");
    }
  }
}
