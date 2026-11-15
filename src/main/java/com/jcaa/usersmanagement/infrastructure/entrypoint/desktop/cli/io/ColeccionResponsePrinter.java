package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ColeccionResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-15s : %s%n";

  private final ConsoleIO console;

  public void print(final ColeccionModel coleccion) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "Numero", coleccion.getNumeroColeccion());
    console.printf(ROW_FORMAT, "Clave de obra", coleccion.getClaveObra());
    console.printf(ROW_FORMAT, "Promotor", coleccion.getPromotor());
    console.printf(ROW_FORMAT, "PVP", coleccion.getPvp());
    console.printf(ROW_FORMAT, "Existencias", coleccion.getExistencias());
    console.printf(ROW_FORMAT, "Stock minimo", coleccion.getStockMinimo());
    console.println(SEPARATOR);
  }

  public void printList(final List<ColeccionModel> colecciones) {
    if (colecciones.isEmpty()) {
      console.println("  No collections found.");
      return;
    }

    console.printf("%n  Total: %d collection(s)%n", colecciones.size());
    colecciones.forEach(this::print);
  }
}
