package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.port.in.GetColeccionByNumeroUseCase;
import com.jcaa.usersmanagement.application.service.dto.query.GetColeccionByNumeroQuery;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ColeccionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindColeccionHandler implements OperationHandler {

  private final GetColeccionByNumeroUseCase getColeccionByNumeroUseCase;
  private final ConsoleIO console;
  private final ColeccionResponsePrinter printer;

  @Override
  public void handle() {
    final int numeroColeccion = console.readInt("\n  Collection number: ");

    final ColeccionModel coleccion =
        getColeccionByNumeroUseCase.execute(
            new GetColeccionByNumeroQuery(numeroColeccion));

    printer.print(coleccion);
  }
}
