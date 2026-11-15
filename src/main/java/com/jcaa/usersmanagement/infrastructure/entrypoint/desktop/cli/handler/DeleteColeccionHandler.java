package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.port.in.DeleteColeccionUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteColeccionCommand;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteColeccionHandler implements OperationHandler {

  private final DeleteColeccionUseCase deleteColeccionUseCase;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final int numeroColeccion = console.readInt("Collection number : ");

    deleteColeccionUseCase.execute(
        new DeleteColeccionCommand(numeroColeccion));

    console.println("\n  Collection deleted successfully.");
  }
}
