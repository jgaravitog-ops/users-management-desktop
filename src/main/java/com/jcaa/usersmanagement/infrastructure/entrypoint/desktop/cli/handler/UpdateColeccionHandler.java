package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.port.in.UpdateColeccionUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateColeccionCommand;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ColeccionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateColeccionHandler implements OperationHandler {

  private final UpdateColeccionUseCase updateColeccionUseCase;
  private final ConsoleIO console;
  private final ColeccionResponsePrinter printer;

  @Override
  public void handle() {
    final int numeroColeccion = console.readInt("Collection number : ");
    final String claveObra = console.readRequired("New work key      : ");
    final String promotor = console.readOptional("New promoter      : ");
    final Double pvp = Double.parseDouble(console.readRequired("New PVP           : "));
    final int existencias = console.readInt("New stock         : ");
    final String stockMinimoInput = console.readOptional("New minimum stock : ");

    final Integer stockMinimo =
        stockMinimoInput.isBlank() ? null : Integer.parseInt(stockMinimoInput);

    final UpdateColeccionCommand command =
        new UpdateColeccionCommand(
            numeroColeccion,
            claveObra,
            promotor,
            pvp,
            existencias,
            stockMinimo);

    final ColeccionModel updated = updateColeccionUseCase.execute(command);

    console.println("\n  Collection updated successfully.");
    printer.print(updated);
  }
}
