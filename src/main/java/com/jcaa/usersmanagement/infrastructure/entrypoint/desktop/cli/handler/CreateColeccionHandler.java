package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.port.in.CreateColeccionUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateColeccionCommand;
import com.jcaa.usersmanagement.domain.model.ColeccionModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ColeccionResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateColeccionHandler implements OperationHandler {

  private final CreateColeccionUseCase createColeccionUseCase;
  private final ConsoleIO console;
  private final ColeccionResponsePrinter printer;

  @Override
  public void handle() {
    final String claveObra = console.readRequired("Clave de obra : ");
    final String promotor = console.readOptional("Promotor       : ");
    final Double pvp = Double.parseDouble(console.readRequired("PVP            : "));
    final int existencias = console.readInt("Existencias    : ");
    final String stockMinimoInput = console.readOptional("Stock minimo   : ");

    final Integer stockMinimo =
        stockMinimoInput.isBlank() ? null : Integer.parseInt(stockMinimoInput);

    final CreateColeccionCommand command =
        new CreateColeccionCommand(claveObra, promotor, pvp, existencias, stockMinimo);

    final ColeccionModel created = createColeccionUseCase.execute(command);

    console.println("\n  Collection created successfully.");
    printer.print(created);
  }
}
